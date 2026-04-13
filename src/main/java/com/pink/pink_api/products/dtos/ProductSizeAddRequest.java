package com.pink.pink_api.products.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSizeAddRequest {
    @NotNull(message = "Size id is required")
    @Min(value = 1, message = "Size id can't be zero")
    private Integer sizeId;

    @NotBlank(message = "Product code is required")
    private String code;

    @NotNull(message = "Quantity id is required")
    @Min(value = 1, message = "Quantity can't be zero")
    private Integer quantity;

    private BigDecimal sellingPrice;
}
