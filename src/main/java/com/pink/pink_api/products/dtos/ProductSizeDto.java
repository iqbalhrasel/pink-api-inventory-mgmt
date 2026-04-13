package com.pink.pink_api.products.dtos;

import com.pink.pink_api.sizes.SizeDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSizeDto {
    private Integer id;
    private SizeDto size;
    private String code;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
}
