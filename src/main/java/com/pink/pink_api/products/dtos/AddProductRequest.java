package com.pink.pink_api.products.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AddProductRequest {
    @NotNull(message = "Category id is required")
    @Min(value = 1, message = "category id can't be zero")
    private Integer categoryId;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Product sizes are required")
    @Size(min = 1, message = "At least one item is required")
    private List<ProductSizeAddRequest> productSizes;
}
