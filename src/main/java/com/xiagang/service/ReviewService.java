package com.xiagang.service;

import com.xiagang.bean.Product;
import com.xiagang.bean.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews(Product p);
    int getReviewsCount(Product p);
}
