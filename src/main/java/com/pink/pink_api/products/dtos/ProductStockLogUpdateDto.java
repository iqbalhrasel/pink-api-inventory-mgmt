package com.pink.pink_api.products.dtos;

import com.pink.pink_api.categories.CategoryDto;
import com.pink.pink_api.products.Type;
import com.pink.pink_api.stocks.StockLogUpdateDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductStockLogUpdateDto {
    private Integer id;
    private CategoryDto category;
    private String name;
    private Type type;
    private List<StockLogUpdateDto> stockLogs;
}
