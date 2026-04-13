package com.pink.pink_api.stocks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StockLogService {
    private final StockLogRepository stockLogRepository;

    public List<StockLogProjection> getStockLogs(StockLogRequest request) {

        LocalDateTime fromDate = request.getFromDate().atStartOfDay();
        LocalDateTime toDate = request.getToDate().atTime(LocalTime.MAX);

        return stockLogRepository.getStockLogs(fromDate, toDate);
    }
}
