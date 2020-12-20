package com.xiagang.dao;

import com.xiagang.bean.Product;
import com.xiagang.bean.Review;
import com.xiagang.bean.User;

import java.util.List;

public interface ReviewDao {
    int insertReview(Review r);

    Review selectReviewById(Integer id);
    List<Review> selectReviewByUser(User user);
    List<Review> selectReviewByProduct(Product p);

    int updateReview(Review r);

    int deleteReview(Integer id);
}
