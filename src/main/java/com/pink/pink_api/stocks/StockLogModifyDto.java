package com.pink.pink_api.stocks;

import com.pink.pink_api.sizes.SizeDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockLogModifyDto {
    private Integer id;
    private SizeDto size;
    private Integer quantity;
    private String code;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
}
