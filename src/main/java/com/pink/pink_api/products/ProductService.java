package com.pink.pink_api.products;

import com.pink.pink_api.categories.CategoryRepository;
import com.pink.pink_api.categories.exceptions.CategoryNotFoundException;
import com.pink.pink_api.products.dtos.*;
import com.pink.pink_api.products.mappers.ProductMapper;
import com.pink.pink_api.sizes.Size;
import com.pink.pink_api.sizes.SizeNotFoundException;
import com.pink.pink_api.sizes.SizeRepository;
import com.pink.pink_api.stocks.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSizeRepository productSizeRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final StockLogRepository stockLogRepository;
    private final ProductMapper productMapper;

    public void addProduct(@Valid AddProductRequest request) {
        var category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        var sizeIds = request
                .getProductSizes()
                .stream()
                .map(ps-> ps.getSizeId())
                .toList();
        var sizes = sizeRepository.findAllById(sizeIds);
        if (sizes.size() != sizeIds.size()) {
            throw new SizeNotFoundException("One or more sizes not found.");
        }

        var product = productMapper.toEntityWithPendingLog(request, sizes);
        product.setCategory(category);
        product.setType(Type.NEW_PRODUCT);

        productRepository.save(product);
    }

    public List<ProductStockLogUpdateDto> getPriceUpdatableProducts() {
        var products = productRepository.getAllProductsWithStockLogs(ApprovalStatus.PENDING);
        products.forEach(p -> Hibernate.initialize(p.getProductSizes()));
        return products
                .stream()
                .map(p-> productMapper.toProductWithStockLogDto(p))
                .toList();
    }

    @Transactional
    public void updateProductPrice(@Valid ProductWithPriceRequest request) {
        var product = productRepository
                .getProductWithSizes(request.getId())
                .orElseThrow(ProductNotFoundException::new);

        product.setType(Type.EXISTING_PRODUCT);
        updateStockAndPrice(request, product);
        productRepository.save(product);

        var stockLogIds = request
                .getStockLogs()
                .stream()
                .map(pm->pm.getId())
                .toList();
        var stockLogs = stockLogRepository.findAllById(stockLogIds);
        if(stockLogIds.size() != stockLogs.size())
            throw new ProductStockLogNotFoundException();

        for(var sl : stockLogs){
            var dto = request
                    .getStockLogs()
                    .stream()
                    .filter(s -> s.getId().equals(sl.getId()))
                    .findFirst()
                    .get();

            sl.setApprovalStatus(ApprovalStatus.APPROVED);
            sl.setCode(dto.getCode());
            sl.setQuantity(dto.getQuantity());
            sl.setBuyingPrice(dto.getBuyingPrice());
            sl.setSellingPrice(dto.getSellingPrice());
        }

        stockLogRepository.saveAll(stockLogs);
    }

    private void updateStockAndPrice(ProductWithPriceRequest request, Product product) {
        for(var pslDto: request.getStockLogs()){
            var productSize = product.getProductSizes().stream()
                    .filter(ps-> ps.getSize().getId().equals(pslDto.getSize().getId()))
                    .findFirst().get();

            var calcBuyingPrice = calculateWeightedAvgPrice(pslDto, productSize);
            productSize.setBuyingPrice(calcBuyingPrice);

            productSize.setSellingPrice(pslDto.getSellingPrice());
            productSize.setQuantity(productSize.getQuantity() + pslDto.getQuantity());
            productSize.setCode(pslDto.getCode());
        }
    }

    private BigDecimal calculateWeightedAvgPrice(StockLogModifyDto stockLogModifyDto, ProductSize productSize) {
        return productSize
                .getBuyingPrice()
                .multiply(BigDecimal.valueOf(productSize.getQuantity()))
                .add(
                        stockLogModifyDto
                                .getBuyingPrice()
                                .multiply(BigDecimal.valueOf(stockLogModifyDto.getQuantity()))
                )
                .divide(
                        BigDecimal.valueOf(productSize.getQuantity() + stockLogModifyDto.getQuantity()),
                        4,
                        RoundingMode.HALF_UP
                );
    }

    public List<ProductOnlyDto> getProductsByCategory(Integer categoryId) {
        var products = productRepository.getAllByCategoryId(categoryId);

        return products.stream()
                .map(p-> productMapper.toProductOnlyDto(p))
                .toList();
    }

    public List<ProductSizeDto> getProductSizesByProduct(Integer productId) {
        var product = productRepository
                .findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        var productSizes = productSizeRepository.findAllByProductId(productId);
        var sizes = sizeRepository.findAll();

        if(productSizes.size() < sizes.size()){
            addNewProductSizes(sizes, productSizes, product);
        }

        return productSizes.stream()
                .map(ps-> productMapper.toProductSizeDto(ps))
                .toList();
    }

    private void addNewProductSizes(List<Size> sizes, List<ProductSize> productSizes, Product product) {
        var sizeIds = sizes.stream()
                .map(s-> s.getId())
                .toList();
        var productSizeIds = productSizes.stream()
                .map(ps-> ps.getSize().getId())
                .toList();

        List<Integer> remainingIds = new ArrayList<>(sizeIds);
        remainingIds.removeAll(productSizeIds);

        List<ProductSize> newProductSizes = new ArrayList<>();
        for(var id: remainingIds){
            var size = sizes.stream().filter(s->s.getId().equals(id)).findFirst().get();

            var newProdSize = new ProductSize();
            newProdSize.setProduct(product);
            newProdSize.setSize(size);
            newProdSize.setCode("");
            newProdSize.setQuantity(0);
            newProdSize.setSellingPrice(BigDecimal.ZERO);
            newProdSize.setBuyingPrice(BigDecimal.ZERO);

            newProductSizes.add(newProdSize);
        }
        productSizeRepository.saveAll(newProductSizes);
        productSizes.addAll(newProductSizes);
    }

    public void addExistingProductQuantity(@Valid ExistingProductQuantityAddRequest request) {
        var product = productRepository
                .findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        var sizeIds = request
                .getProductSizes()
                .stream()
                .map(ps-> ps.getSizeId())
                .toList();
        var sizes = sizeRepository.findAllById(sizeIds);
        if (sizes.size() != sizeIds.size()) {
            throw new SizeNotFoundException("One or more sizes not found.");
        }

        List<StockLog> stockLogList = new ArrayList<>();
        for(var prodSize : request.getProductSizes()){
            var log = new StockLog();
            log.setProduct(product);
            log.setCode(prodSize.getCode());
            log.setQuantity(prodSize.getQuantity());
            log.setSellingPrice(prodSize.getSellingPrice());
            log.setApprovalStatus(ApprovalStatus.PENDING);

            var size = sizes
                    .stream()
                    .filter(s-> s.getId().equals(prodSize.getSizeId()))
                    .findFirst()
                    .get();
            log.setSize(size);
            stockLogList.add(log);
        }
        stockLogRepository.saveAll(stockLogList);
    }

    public List<ProductSizeDto> getProductsSizesBy(Integer productId) {
        var productSizes = productSizeRepository.findAllByProductId(productId);
        return productSizes.stream()
                .map(ps-> productMapper.toProductSizeDto(ps))
                .toList();
    }

    public List<ProductWithProductSizeDto> getStockReport() {
        var products = productRepository.getAllProductsWithSizes();
        return products.stream()
                .map(p-> productMapper.toProductWithProductSizeDto(p))
                .toList();
    }

    public List<ApprovableProductWithLog> getApprovableProducts() {
        var productsWithMovements = productRepository.getAllProductsWithStockLogs(ApprovalStatus.PENDING);

        return productsWithMovements.stream()
                .map(p-> productMapper.toApprovableProductWithStockLog(p))
                .toList();
    }

    public void modifyProductStocks(Integer productId, ModifyStockLogRequest request) {
        if(!productRepository.existsById(productId))
            throw new ProductNotFoundException();

        var productStockIds = request.getStockLogs().stream()
                .map(psl-> psl.getId()).toList();
        var productStockLogs = stockLogRepository.findAllById(productStockIds);

        if(productStockIds.size() != productStockLogs.size())
            throw new ProductStockLogNotFoundException();

        for(var psl: productStockLogs){
            var logDto = request.getStockLogs().stream()
                    .filter(pmd-> pmd.getId().equals(psl.getId()))
                    .findFirst().get();
            psl.setQuantity(logDto.getQuantity());
            psl.setCode(logDto.getCode());
            psl.setSellingPrice(logDto.getSellingPrice());
        }

        stockLogRepository.saveAll(productStockLogs);
    }
}
