package com.pink.pink_api.customers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerPhone}")
    public List<CustomerDto> getCustomerByPhone(@PathVariable(name = "customerPhone") String customerPhone){
        return customerService.getCustomerByPhone(customerPhone);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> addRemarks(@PathVariable(name = "customerId") Integer customerId,
                                        @RequestBody @Valid AddRemarksRequest request){
        customerService.addRemarks(customerId, request);
        return ResponseEntity.ok().build();
    }
}
