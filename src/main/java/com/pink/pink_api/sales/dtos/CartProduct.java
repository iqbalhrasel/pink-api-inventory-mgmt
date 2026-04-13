package com.pink.pink_api.sales.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProduct {
    @NotNull(message = "Product id is required")
    private Integer productId;

    private String productName;

    @NotNull(message = "Size id is required")
    private Integer sizeId;

    private String sizeName;

    private String code;
    private BigDecimal sellingPrice;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}
