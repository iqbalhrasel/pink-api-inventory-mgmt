package com.pink.pink_api.stocks;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StockLogProjection {
    Integer getId();
    LocalDateTime getImportedAt();
    String getProductName();
    String getSizeName();
    String getCode();
    Integer getQuantity();
    BigDecimal getBuyingPrice();
    BigDecimal getSellingPrice();
}
