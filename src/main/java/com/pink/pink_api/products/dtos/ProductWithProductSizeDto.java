package com.pink.pink_api.products.dtos;

import com.pink.pink_api.categories.CategoryDto;
import com.pink.pink_api.products.Type;
import lombok.Data;

import java.util.List;

@Data
public class ProductWithProductSizeDto {
    private Integer id;
    private CategoryDto category;
    private String name;
    private Type type;
    private List<ProductSizeDto> productSizes;
}
