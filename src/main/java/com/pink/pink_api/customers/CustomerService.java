package com.pink.pink_api.customers;

import com.pink.pink_api.customers.mappers.CustomerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDto> getCustomerByPhone(String customerPhone) {
        String phone = customerPhone.replace(" ", "").replace("+88","");
        if(phone.startsWith("88")){
            phone = phone.substring(2);
        }

        var customers = customerRepository
                .getCustomerByPhone(phone);

        return customers.stream()
                .map(c-> customerMapper.toDto(c))
                .toList();
    }

    public void addRemarks(Integer customerId, @Valid AddRemarksRequest request) {
        var customer = customerRepository
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        customer.setRemarks(request.getRemarks());
        customerRepository.save(customer);
    }
}
