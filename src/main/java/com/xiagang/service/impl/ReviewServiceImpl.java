package com.xiagang.service.impl;

import com.xiagang.bean.Product;
import com.xiagang.bean.Review;
import com.xiagang.dao.ReviewDao;
import com.xiagang.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    private ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getReviews(Product p) {
        return reviewDao.selectReviewByProduct(p);
    }

    @Override
    public int getReviewsCount(Product p) {
        return reviewDao.selectReviewCount(p);
    }
}
