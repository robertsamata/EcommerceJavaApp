package com.example.ecommerceApp.repository;

import com.example.ecommerceApp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}