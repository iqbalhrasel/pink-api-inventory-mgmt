package com.pink.pink_api.products.dtos;

import com.pink.pink_api.stocks.StockLogModifyDto;
import lombok.Data;

import java.util.List;

@Data
public class ModifyStockLogRequest {
    private List<StockLogModifyDto> stockLogs;
}
