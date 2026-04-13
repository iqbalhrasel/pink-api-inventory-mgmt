package com.pink.pink_api.sizes;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SizeAddRequest {
    @NotBlank(message = "Size name is required")
    private String name;
}
