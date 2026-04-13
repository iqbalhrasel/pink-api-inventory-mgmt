package com.pink.pink_api.stocks;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockLogRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
}
