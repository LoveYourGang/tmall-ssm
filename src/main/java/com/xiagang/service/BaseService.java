package com.xiagang.service;

import com.xiagang.bean.Category;
import com.xiagang.bean.Order;
import com.xiagang.bean.Product;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

public interface BaseService {
    void fillCategory(Category c);
    void fillOrder(Order order);
    void fillProduct(Product p);
    InputStream parseUpload(HttpServletRequest request, Map<String, String> params);
    boolean uploadImage(InputStream is, File file);
}
