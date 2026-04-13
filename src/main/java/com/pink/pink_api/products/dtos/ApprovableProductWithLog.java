package com.pink.pink_api.products.dtos;

import com.pink.pink_api.stocks.StockLogDto;
import lombok.Data;

import java.util.List;

@Data
public class ApprovableProductWithLog {
    private ProductOnlyDto product;
    private List<StockLogDto> stockLogs;
}
