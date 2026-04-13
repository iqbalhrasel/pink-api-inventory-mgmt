package com.pink.pink_api.sales;

import com.pink.pink_api.customers.mappers.CustomerMapper;
import com.pink.pink_api.products.InsufficientProductStockException;
import com.pink.pink_api.products.ProductNotFoundException;
import com.pink.pink_api.products.ProductRepository;
import com.pink.pink_api.sales.dtos.*;
import com.pink.pink_api.sales.mappers.SalesMapper;
import com.pink.pink_api.users.UserNotFoundException;
import com.pink.pink_api.users.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SalesService {
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;
    private final SalesMapper salesMapper;

    @Transactional
    public void addSales(@Valid AddSalesRequest request) {
        System.out.println(request);
        var customer = customerMapper.toEntity(request.getCustomer());

        var sale = new Sale();
        sale.setDiscount(request.getDiscount());
        sale.setTotalAmount(request.getTotalAmount());
        sale.setShoppingType(request.getCustomer().getShoppingType());
        sale.setDeliveryVendor(request.getCustomer().getDeliverVendor());
        sale.setLocationType(request.getCustomer().getLocationType());

        var userId = Integer.parseInt(
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
        var username = userRepository.getUsernameBy(userId).orElseThrow(UserNotFoundException::new);
        sale.setUsername(username);

        customer.setSale(sale);
        sale.setCustomer(customer);

        var productIds = request.getProducts().stream()
                .map(p-> p.getProductId())
                .distinct().toList();
        var getProductsWithSizes = productRepository.getProductsWithSizes(productIds);
        if(productIds.size() != getProductsWithSizes.size())
            throw new ProductNotFoundException();

        List<SaleProduct> saleProductList = new ArrayList<>();
        for(var cartProd : request.getProducts()){
            var saleProduct = new SaleProduct();

            var product = getProductsWithSizes.stream()
                    .filter(p-> p.getId().equals(cartProd.getProductId()))
                    .findFirst().get();

            saleProduct.setProduct(product);

            var productSize = product.getProductSizes().stream()
                    .filter(ps-> ps.getSize().getId().equals(cartProd.getSizeId()))
                    .findFirst().get();
            if(cartProd.getQuantity() > productSize.getQuantity()){
                throw new InsufficientProductStockException();
            }

            saleProduct.setProductSize(productSize);
            saleProduct.setQuantity(cartProd.getQuantity());
            saleProduct.setBuyingPrice(productSize.getBuyingPrice());
            saleProduct.setSellingPrice(productSize.getSellingPrice());
            saleProduct.setSale(sale);

            saleProductList.add(saleProduct);

            productSize.setQuantity(productSize.getQuantity() - cartProd.getQuantity());
        }
        sale.setSaleProducts(saleProductList);

        productRepository.saveAll(getProductsWithSizes);
        salesRepository.save(sale);
    }

    public List<SalesReportProjection> getSalesReport(@Valid SalesReportRequest request) {
        LocalDateTime fromDate = request.getFromDate().atStartOfDay();
        LocalDateTime toDate = request.getToDate().atTime(LocalTime.MAX);
        List<SalesReportProjection> salesReportProjections;
        if(request.getSalesType() == SalesType.ALL){
            salesReportProjections = salesRepository.getSalesReport(fromDate, toDate);
        }else{
            salesReportProjections = salesRepository.getSalesReportByType(request.getSalesType().name(), fromDate, toDate);
        }

        return salesReportProjections;
    }

    public List<PurchaseHistoryProjection> getPurchaseHistory(Integer salesId) {
        if(!salesRepository.existsById(salesId))
            throw new SaleInfoNotFoundException();

        return salesRepository.getCustomerPurchaseHistory(salesId);
    }
}
