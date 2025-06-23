package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Review;
import com.example.ecommerceApp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review updateReview(Long id, Review review) {
        return reviewRepository.findById(id)
                .map(existing -> {
                    existing.setComment(review.getComment());
                    existing.setRating(review.getRating());
                    existing.setProduct(review.getProduct());
                    return reviewRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
