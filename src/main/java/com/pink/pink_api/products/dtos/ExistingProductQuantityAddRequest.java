package com.pink.pink_api.products.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExistingProductQuantityAddRequest {
    @NotNull(message = "Product Id is required")
    private Integer productId;

    @NotNull(message = "Product Sizes are required")
    @Size(min = 1, message = "At least one item is required")
    private List<@Valid ProductSizeAddRequest> productSizes;
}
