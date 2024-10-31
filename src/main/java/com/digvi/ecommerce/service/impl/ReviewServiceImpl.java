package com.digvi.ecommerce.service.impl;

import com.digvi.ecommerce.model.Product;
import com.digvi.ecommerce.model.Review;
import com.digvi.ecommerce.model.User;
import com.digvi.ecommerce.repository.ReviewRepository;
import com.digvi.ecommerce.request.CreateReviewRequest;
import com.digvi.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setReviewText(req.getReviewText());
        review.setProduct(product);
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());
        product.getReviews().add(review);

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception {
        Review review = getReviewById(reviewId);

        if(!review.getUser().getId().equals(userId)){
            throw new Exception("You can't update this review");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(!review.getUser().getId().equals(userId)){
            throw new Exception("You can't delete this review");
        }
        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) throws Exception {
        Review review = reviewRepository.getReviewById(reviewId);
        if(review == null){
            throw new Exception("Review not found with this id "+reviewId);
        }
        return review;
    }
}
