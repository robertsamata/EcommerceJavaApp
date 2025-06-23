package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
