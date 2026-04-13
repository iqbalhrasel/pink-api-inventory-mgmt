package com.pink.pink_api.products.mappers;

import com.pink.pink_api.categories.CategoryDto;
import com.pink.pink_api.products.Product;
import com.pink.pink_api.products.ProductSize;
import com.pink.pink_api.products.Type;
import com.pink.pink_api.products.dtos.*;
import com.pink.pink_api.sizes.Size;
import com.pink.pink_api.sizes.SizeDto;
import com.pink.pink_api.stocks.ApprovalStatus;
import com.pink.pink_api.stocks.StockLog;
import com.pink.pink_api.stocks.StockLogDto;
import com.pink.pink_api.stocks.StockLogUpdateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {
    public Product toEntityWithPendingLog(AddProductRequest request, List<Size> sizes){
        if(request == null)
            return null;
        var product = new Product();
        product.setName(request.getName());

        List<ProductSize> productSizeList = new ArrayList<>();

        for(var reqProdSize : request.getProductSizes()){
            var productSize = new ProductSize();
            productSize.setProduct(product);
            productSize.setCode(reqProdSize.getCode());
            productSize.setQuantity(0);
            productSize.setBuyingPrice(BigDecimal.ZERO);
            productSize.setSellingPrice(BigDecimal.ZERO);

            var size = sizes
                    .stream()
                    .filter(s-> s.getId().equals(reqProdSize.getSizeId()))
                    .findFirst()
                    .get();
            productSize.setSize(size);

            productSizeList.add(productSize);
        }
        product.setProductSizes(productSizeList);

        List<StockLog> stockLogList = new ArrayList<>();
        for(var reqProdSize : request.getProductSizes()){
            var log = new StockLog();
            log.setProduct(product);
            log.setCode(reqProdSize.getCode());
            log.setQuantity(reqProdSize.getQuantity());
            log.setSellingPrice(reqProdSize.getSellingPrice());
            log.setApprovalStatus(ApprovalStatus.PENDING);

            var size = sizes
                    .stream()
                    .filter(s-> s.getId().equals(reqProdSize.getSizeId()))
                    .findFirst()
                    .get();
            log.setSize(size);

            stockLogList.add(log);
        }
        product.setStockLogs(stockLogList);

        return product;
    }

    public ProductStockLogUpdateDto toProductWithStockLogDto(Product product){
        var pslDto = new ProductStockLogUpdateDto();
        pslDto.setId(product.getId());
        pslDto.setName(product.getName());
        pslDto.setType(product.getType());
        pslDto.setCategory(new CategoryDto(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );

        List<StockLogUpdateDto> stockLogDtoList = new ArrayList<>();
        for(var pm: product.getStockLogs()){
            var logDto = new StockLogUpdateDto();
            logDto.setId(pm.getId());
            logDto.setQuantity(pm.getQuantity());
            logDto.setCode(pm.getCode());
            logDto.setSize(new SizeDto(pm.getSize().getId(), pm.getSize().getName()));

            if(product.getType() == Type.EXISTING_PRODUCT){
                var productSize = product.getProductSizes().stream()
                        .filter(ps-> ps.getSize().getId().equals(pm.getSize().getId()))
                        .findFirst().get();
                logDto.setBuyingPrice(productSize.getBuyingPrice());
                logDto.setSellingPrice(productSize.getSellingPrice());
            }

            logDto.setNewSellingPrice(pm.getSellingPrice());
            stockLogDtoList.add(logDto);
        }
        pslDto.setStockLogs(stockLogDtoList);

        return pslDto;
    }

    public ProductOnlyDto toProductOnlyDto(Product product){
        if(product == null)
            return null;

        var dto = new ProductOnlyDto();
        dto.setId(product.getId());
        dto.setCategory(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()));
        dto.setName(product.getName());
        dto.setType(product.getType());

        return dto;
    }

    public ProductSizeDto toProductSizeDto(ProductSize productSize){
        if(productSize == null)
            return null;

        var dto = new ProductSizeDto();
        dto.setId(productSize.getId());
        dto.setSize(new SizeDto(productSize.getSize().getId(), productSize.getSize().getName()));
        dto.setCode(productSize.getCode());
        dto.setQuantity(productSize.getQuantity());
        dto.setBuyingPrice(productSize.getBuyingPrice());
        dto.setSellingPrice(productSize.getSellingPrice());

        return dto;
    }

    public ProductWithProductSizeDto toProductWithProductSizeDto(Product product){
        var productDto = new ProductWithProductSizeDto();
        productDto.setId(product.getId());
        productDto.setCategory(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()));
        productDto.setName(product.getName());
        productDto.setType(product.getType());

        List<ProductSizeDto> productSizeDtoList = new ArrayList<>();
        for(var prodSize: product.getProductSizes()){
            var psDto = new ProductSizeDto();
            psDto.setId(prodSize.getId());
            psDto.setSize(new SizeDto(prodSize.getSize().getId(), prodSize.getSize().getName()));
            psDto.setCode(prodSize.getCode());
            psDto.setQuantity(prodSize.getQuantity());
            psDto.setBuyingPrice(prodSize.getBuyingPrice());
            psDto.setSellingPrice(prodSize.getSellingPrice());

            productSizeDtoList.add(psDto);
        }

        productDto.setProductSizes(productSizeDtoList);

        return productDto;
    }

    public ApprovableProductWithLog toApprovableProductWithStockLog(Product product){
        var approvableProduct = new ApprovableProductWithLog();

        var productOnlyDto = new ProductOnlyDto();
        productOnlyDto.setId(product.getId());
        productOnlyDto.setName(product.getName());
        productOnlyDto.setType(product.getType());
        productOnlyDto.setCategory(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()));

        approvableProduct.setProduct(productOnlyDto);

        List<StockLogDto> stockLogList = new ArrayList<>();
        for(var pm : product.getStockLogs()){
            var log = new StockLogDto();
            log.setId(pm.getId());
            log.setCode(pm.getCode());
            log.setSize(new SizeDto(pm.getSize().getId(), pm.getSize().getName()));
            log.setQuantity(pm.getQuantity());
            log.setSellingPrice(pm.getSellingPrice());
            log.setBuyingPrice(pm.getBuyingPrice());
            log.setApprovalStatus(pm.getApprovalStatus());
            log.setImportedAt(pm.getImportedAt());

            stockLogList.add(log);
        }
        approvableProduct.setStockLogs(stockLogList);

        return approvableProduct;
    }
}
