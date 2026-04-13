package com.pink.pink_api.customers;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Integer id;
    private Integer saleId;
    private LocalDateTime createdAt;
    private String name;
    private String phone;
    private ShoppingType shoppingType;
    private String address;
    private String remarks;
}
