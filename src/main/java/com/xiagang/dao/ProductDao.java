package com.xiagang.dao;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    int insertProduct(Product product);

    Product selectProductById(Integer id);
    Product selectProductByName(String name);
    List<Product> selectProducts();
    List<Product> selectProductByCategory(Category c);
    List<Product> selectProductByCategoryLimit(@Param("c") Category c, @Param("start") Integer start, @Param("count") Integer count);

    int updateProduct(Product product);

    int deleteProduct(Integer id);
}
