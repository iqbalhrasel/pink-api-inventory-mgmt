package com.pink.pink_api.categories;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryAddRequest request){
        CategoryDto categoryDto = categoryService.addCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "categoryId") Integer categoryId,
                                                   @RequestBody @Valid UpdateCategoryRequest request){
        categoryService.updateCategory(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
