package com.digvi.ecommerce.service;

import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.Review;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req,
                        User user,
                        Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
