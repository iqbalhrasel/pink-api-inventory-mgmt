package com.pink.pink_api.sales;

import com.pink.pink_api.sales.dtos.PurchaseHistoryProjection;
import com.pink.pink_api.sales.dtos.SalesReportProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sale, Integer> {
    boolean existsById(Integer id);

    @Query(value = "CALL get_sales_report(:from_date, :to_date)", nativeQuery = true)
    List<SalesReportProjection> getSalesReport(@Param(value = "from_date") LocalDateTime fromDate,
                                               @Param(value = "to_date") LocalDateTime toDate);

    @Query(value = "CALL get_sales_report_by_type(:type, :from_date, :to_date)", nativeQuery = true)
    List<SalesReportProjection> getSalesReportByType(@Param(value = "type") String type,
                                                     @Param(value = "from_date") LocalDateTime fromDate,
                                                     @Param(value = "to_date") LocalDateTime toDate);

    @Query(value = "CALL get_customer_purchase_history(:salesId)", nativeQuery = true)
    List<PurchaseHistoryProjection> getCustomerPurchaseHistory(@Param(value = "salesId") Integer salesId);
}
