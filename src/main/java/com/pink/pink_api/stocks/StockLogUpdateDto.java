package com.pink.pink_api.stocks;

import com.pink.pink_api.sizes.SizeDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockLogUpdateDto {
    private Integer id;
    private SizeDto size;
    private Integer quantity;
    private String code;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private BigDecimal newSellingPrice;
}
