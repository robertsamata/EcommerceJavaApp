package com.example.ecommerceApp.repository;

import com.example.ecommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
