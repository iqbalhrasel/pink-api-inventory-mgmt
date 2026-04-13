package com.pink.pink_api.sales.dtos;

import com.pink.pink_api.customers.ShoppingType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesHistory {
    private Integer id;
    private LocalDateTime createdAt;
    private String customerName;
    private String customerPhone;
    private Integer totalItems;
    private BigDecimal totalBuyingPrice;
    private BigDecimal discount;
    private ShoppingType shoppingType;
    private String username;
    private BigDecimal totalAmount;
}
