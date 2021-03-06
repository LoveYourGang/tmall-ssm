package com.xiagang.service;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;

import java.util.List;

public interface ProductService {
    int addProduct(Product p);

    List<Product> getProducts();
    List<Product> getProducts(Category c);
    Product getProduct(Integer id);
    List<Product> searchProducts(String keyword);

    int modifyProduct(Product p);

    int deleteProduct(Integer id);
}
