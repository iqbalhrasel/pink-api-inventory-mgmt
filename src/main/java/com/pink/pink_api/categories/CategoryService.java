package com.pink.pink_api.categories;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(c-> new CategoryDto(c.getId(), c.getName()))
                .toList();
    }

    public CategoryDto addCategory(@Valid CategoryAddRequest request) {
        var category = new Category();
        category.setName(request.getName());

        categoryRepository.save(category);

        return new CategoryDto(category.getId(), category.getName());
    }

    public void updateCategory(Integer categoryId, @Valid UpdateCategoryRequest request) {
        var category = categoryRepository
                .findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        category.setName(request.getName());
        categoryRepository.save(category);
    }
}
