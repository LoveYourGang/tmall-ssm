package com.xiagang.dao;

import com.xiagang.bean.Product;
import com.xiagang.bean.Review;
import com.xiagang.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReviewDao {
    int insertReview(Review r);

    Review selectReviewById(Integer id);
    Review selectReviewByUserProduct(@Param("user") User user, @Param("p") Product p);
    List<Review> selectReviewByUser(User user);
    List<Review> selectReviewByProduct(Product p);
    int selectReviewCount(Product p);

    int updateReview(Review r);

    int deleteReview(Integer id);
}
