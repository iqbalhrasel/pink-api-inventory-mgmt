package com.pink.pink_api.sales.dtos;

import com.pink.pink_api.customers.LocationType;
import com.pink.pink_api.customers.ShoppingType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SalesReportProjection {
    Integer getId();
    LocalDateTime getCreatedAt();
    String getCustomerName();
    String getCustomerPhone();
    Integer getTotalItems();
    BigDecimal getTotalBuyingPrice();
    BigDecimal getDiscount();
    LocationType getLocationType();
    ShoppingType getShoppingType();
    String getUsername();
    BigDecimal getTotalAmount();
}
