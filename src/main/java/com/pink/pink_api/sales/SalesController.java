package com.pink.pink_api.sales;

import com.pink.pink_api.sales.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sales")
public class SalesController {
    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<?> addSales(@RequestBody @Valid AddSalesRequest request){
        salesService.addSales(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/report")
    public List<SalesReportProjection> getSalesReport(@RequestBody @Valid SalesReportRequest request){
        return salesService.getSalesReport(request);
    }

    @GetMapping("/{salesId}")
    public List<PurchaseHistoryProjection> getPurchaseHistory(@PathVariable(name = "salesId") Integer salesId){
        return salesService.getPurchaseHistory(salesId);
    }
}
