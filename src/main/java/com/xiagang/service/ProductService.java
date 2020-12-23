package com.xiagang.service;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    List<Product> getProducts(Category c);
    Product getProduct(Integer id);
}
