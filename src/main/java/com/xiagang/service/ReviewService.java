package com.xiagang.service;

import com.xiagang.bean.Order;
import com.xiagang.bean.Product;
import com.xiagang.bean.Review;

import java.util.List;

public interface ReviewService {
    int addReview(Review r);

    List<Review> getReviews(Product p);
    int getReviewsCount(Product p);
}
