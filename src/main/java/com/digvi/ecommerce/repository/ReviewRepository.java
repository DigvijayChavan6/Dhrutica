package com.digvi.ecommerce.repository;

import com.digvi.ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    Review getReviewById(Long reviewId);
}
