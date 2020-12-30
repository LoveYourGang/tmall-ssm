package com.xiagang.service;

import com.xiagang.bean.ProductImage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ProductImageService {
    int addProductImage(HttpServletRequest request, Map<String, String> params);

    ProductImage getProductImage(Integer id);

    int deleteProductImage(HttpServletRequest request, Integer id);
}
