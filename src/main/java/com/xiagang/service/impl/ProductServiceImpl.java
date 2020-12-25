package com.xiagang.service.impl;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;
import com.xiagang.bean.ProductImage;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import com.xiagang.dao.ReviewDao;
import com.xiagang.service.FillService;
import com.xiagang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private ProductImageDao productImageDao;
    private ReviewDao reviewDao;
    private OrderItemDao orderItemDao;
    private FillService fill;

    @Autowired
    public ProductServiceImpl(ProductDao productDao,
                              ProductImageDao productImageDao,
                              ReviewDao reviewDao,
                              OrderItemDao orderItemDao,
                              FillService fill) {
        this.productDao = productDao;
        this.productImageDao = productImageDao;
        this.reviewDao = reviewDao;
        this.orderItemDao = orderItemDao;
        this.fill = fill;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = productDao.selectProducts();
        for(Product p: products) {
            fill.fillProduct(p);
        }
        return products;
    }

    @Override
    public List<Product> getProducts(Category c) {
        List<Product> products = productDao.selectProductByCategory(c);
        products.forEach(p -> fill.fillProduct(p));
        return products;
    }

    @Override
    public Product getProduct(Integer id) {
        Product product = productDao.selectProductById(id);
        fill.fillProduct(product);
        return product;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> products = productDao.selectProductByName(keyword);
        products.forEach(fill::fillProduct);
        return products;
    }

//    private void fillProduct(Product p) {
//        List<ProductImage> pisAll = productImageDao.selectProductImageByProduct(p);
//        List<ProductImage> pisSingle = productImageDao.selectProductImageByProductType(p, ProductImage.SINGLE);
//        List<ProductImage> pisDetail = productImageDao.selectProductImageByProductType(p, ProductImage.DETAIL);
//        p.setProductImages(pisAll);
//        p.setProductSingleImages(pisSingle);
//        p.setProductDetailImages(pisDetail);
//        if(!pisAll.isEmpty())
//            p.setFirstProductImage(pisAll.get(0));
//        p.setReviewCount(reviewDao.selectReviewCount(p));
//        p.setSaleCount(orderItemDao.selectSaleCount(p));
//    }
}
