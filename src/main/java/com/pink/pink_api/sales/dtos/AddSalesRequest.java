package com.pink.pink_api.sales.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddSalesRequest {
    @Valid
    @NotNull(message = "Customer info is required")
    private AddCustomerRequest customer;

    private BigDecimal discount;

    @NotNull(message = "total price is required")
    private BigDecimal totalAmount;

    @NotNull(message = "Cart Products are required")
    @Size(min = 1, message = "At least one item is required")
    private List<@Valid CartProduct> products;
}
