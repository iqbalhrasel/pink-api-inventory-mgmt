package com.pink.pink_api.stocks;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockLogController {
    private final StockLogService stockLogService;

    @PostMapping
    private List<StockLogProjection> getStockLogs(@RequestBody StockLogRequest request){
        return stockLogService.getStockLogs(request);
    }
}
