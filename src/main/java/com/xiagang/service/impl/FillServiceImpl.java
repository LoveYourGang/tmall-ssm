package com.xiagang.service.impl;

import com.xiagang.bean.*;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import com.xiagang.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FillServiceImpl implements FillService {
    private ProductDao productDao;
    private OrderItemDao orderItemDao;
    private ProductImageDao productImageDao;

    @Autowired
    public FillServiceImpl(ProductDao pDao, OrderItemDao oiDao, ProductImageDao piDao) {
        productDao = pDao;
        orderItemDao = oiDao;
        productImageDao = piDao;
    }

    @Override
    public void fillCategory(Category c) {
        List<Product> products = productDao.selectProductByCategory(c);
        for(Product p: products) {
            fillProduct(p);
        }
        c.setProducts(products);
        int start, count=5;
        List<List<Product>> productsByRow = new ArrayList<>();
//        List<Product> ps = productDao.selectProductByCategoryLimit(c, start, count);
//        while(!ps.isEmpty()) {
//            for(Product p: ps) {
//                fillProduct(p);
//            }
//            productsByRow.add(ps);
//            start += count;
//            ps = productDao.selectProductByCategoryLimit(c, start, count);
//        }
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
    }
}
