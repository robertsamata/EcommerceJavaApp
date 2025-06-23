package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review createReview(Review review);
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long id);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
}

