package com.pink.pink_api.sales.dtos;

import com.pink.pink_api.customers.LocationType;
import com.pink.pink_api.customers.ShoppingType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCustomerRequest {
    @NotBlank(message = "Customer name is required")
    private String name;

    @NotBlank(message = "Customer phone is required")
    private String phone;

    @NotBlank(message = "Customer address is required")
    private String address;

    private LocationType locationType;
    private ShoppingType shoppingType;
    private String deliverVendor;
}
