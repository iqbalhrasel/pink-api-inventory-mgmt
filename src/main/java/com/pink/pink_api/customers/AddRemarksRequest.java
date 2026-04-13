package com.pink.pink_api.customers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddRemarksRequest {
    @NotBlank(message = "Remarks field can't be empty")
    private String remarks;
}
