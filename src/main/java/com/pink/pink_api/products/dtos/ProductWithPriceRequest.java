package com.pink.pink_api.products.dtos;

import com.pink.pink_api.categories.CategoryDto;
import com.pink.pink_api.stocks.StockLogModifyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ProductWithPriceRequest {
    @NotNull(message = "Product id is required")
    @Min(value = 1, message = "Size id can't be zero")
    private Integer id;

    @Valid
    @NotNull(message = "Category is required")
    private CategoryDto category;

    private String name;

    @NotNull(message = "Product movements are required")
    @Size(min = 1, message = "At least one item is required")
    private List<@Valid StockLogModifyDto> stockLogs;
}
