package com.xiagang.dao;

import com.xiagang.bean.Product;
import com.xiagang.bean.ProductImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductImageDao {
    int insertProductImage(ProductImage pi);

    ProductImage selectProductImageById(Integer id);
    List<ProductImage> selectProductImageByProduct(Product p);
    List<ProductImage> selectProductImageByProductType(@Param("p") Product p, @Param("type") String type);

    int updateProductImage(ProductImage pi);

    int deleteProductImage(Integer id);
}
