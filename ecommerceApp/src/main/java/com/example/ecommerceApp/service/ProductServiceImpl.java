package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Category;
import com.example.ecommerceApp.model.Product;
import com.example.ecommerceApp.repository.CategoryRepository;
import com.example.ecommerceApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product produs) {
        return productRepository.save(produs);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product addCategory(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.getCategories().add(category);
        return productRepository.save(product);
    }
}
