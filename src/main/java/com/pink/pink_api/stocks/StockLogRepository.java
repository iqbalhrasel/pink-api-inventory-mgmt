package com.pink.pink_api.stocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StockLogRepository extends JpaRepository<StockLog, Integer> {
    @Query(value = "CALL get_stock_logs(:from_date, :to_date)", nativeQuery = true)
    List<StockLogProjection> getStockLogs(@Param(value = "from_date") LocalDateTime fromDate,
                                               @Param(value = "to_date") LocalDateTime toDate);



}
