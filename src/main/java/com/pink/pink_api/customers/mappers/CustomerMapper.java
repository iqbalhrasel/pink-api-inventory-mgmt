package com.pink.pink_api.customers.mappers;

import com.pink.pink_api.customers.Customer;
import com.pink.pink_api.customers.CustomerDto;
import com.pink.pink_api.sales.dtos.AddCustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(AddCustomerRequest request){
        if(request == null)
            return null;

        var customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setShoppingType(request.getShoppingType());
        customer.setAddress(request.getAddress());

        return customer;
    }

    public CustomerDto toDto(Customer customer){
        if(customer == null)
            return null;
        var dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setSaleId(customer.getSale().getId());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setShoppingType(customer.getShoppingType());
        dto.setAddress(customer.getAddress());
        dto.setRemarks(customer.getRemarks());

        return dto;
    }
}
