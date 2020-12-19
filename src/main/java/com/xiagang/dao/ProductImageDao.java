package com.xiagang.dao;

import com.xiagang.bean.Product;
import com.xiagang.bean.ProductImage;

import java.util.List;

public interface ProductImageDao {
    int insertProductImage(ProductImage pi);

    ProductImage selectProductImageById(Integer id);
    List<ProductImage> selectProductImageByProduct(Product p);

    int updateProductImage(ProductImage pi);

    int deleteProductImage(Integer id);
}
