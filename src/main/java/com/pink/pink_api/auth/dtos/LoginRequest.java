package com.pink.pink_api.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
}