package com.xiagang.service.impl;

import com.xiagang.bean.*;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import com.xiagang.dao.ReviewDao;
import com.xiagang.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fill")
public class FillServiceImpl implements FillService {
    private ProductDao productDao;
    private OrderItemDao orderItemDao;
    private ProductImageDao productImageDao;
    private ReviewDao reviewDao;

    @Autowired
    public FillServiceImpl(ProductDao pDao, OrderItemDao oiDao, ProductImageDao piDao, ReviewDao reviewDao) {
        productDao = pDao;
        orderItemDao = oiDao;
        productImageDao = piDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public void fillCategory(Category c) {
        List<Product> products = productDao.selectProductByCategory(c);//productService.getProducts(c);
        products.forEach(this::fillProduct);
        c.setProducts(products);
        int start, count=5;
        List<List<Product>> productsByRow = new ArrayList<>();
        for(start=0; start < products.size(); start += count) {
            List<Product> ps = new ArrayList<>(count);
            for(int i=0; i < count && start+i < products.size(); i++) {
                ps.add(products.get(start+i));
            }
            productsByRow.add(ps);
        }
        c.setProductsByRow(productsByRow);
    }

    @Override
    public void fillOrder(Order order) {
        List<OrderItem> ois = orderItemDao.selectOrderItemByOrder(order);
        ois.forEach(oi -> fillProduct(oi.getProduct()));
        order.setOrderItems(ois);
    }

    @Override
    public void fillProduct(Product p) {
        List<ProductImage> pisAll = productImageDao.selectProductImageByProduct(p);
        List<ProductImage> pisSingle = productImageDao.selectProductImageByProductType(p, ProductImage.SINGLE);
        List<ProductImage> pisDetail = productImageDao.selectProductImageByProductType(p, ProductImage.DETAIL);
        p.setProductImages(pisAll);
        p.setProductSingleImages(pisSingle);
        p.setProductDetailImages(pisDetail);
        if(!pisAll.isEmpty())
            p.setFirstProductImage(pisAll.get(0));
        p.setReviewCount(reviewDao.selectReviewCount(p));
        p.setSaleCount(orderItemDao.selectSaleCount(p));
    }
}
