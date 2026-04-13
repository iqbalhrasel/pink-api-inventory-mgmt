package com.pink.pink_api.categories;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {
    @NotNull(message = "Category id is required")
    @Min(value = 1, message = "Category id can't be zero")
    private Integer id;

    @NotBlank(message = "Category name is required")
    private String name;
}
