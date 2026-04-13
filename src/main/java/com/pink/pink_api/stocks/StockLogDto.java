package com.pink.pink_api.stocks;

import com.pink.pink_api.sizes.SizeDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockLogDto {
    private Integer id;
    private LocalDateTime importedAt;
    private SizeDto size;
    private String code;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private ApprovalStatus approvalStatus;
}
