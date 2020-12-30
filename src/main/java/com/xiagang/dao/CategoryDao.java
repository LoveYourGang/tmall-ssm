package com.xiagang.dao;

import com.xiagang.bean.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao {
    int insertCategory(Category c);

    Category selectCategoryById(Integer id);
    Category selectCategoryByName(String name);
    List<Category> selectAllCategories();
    List<Category> selectCategoryLimit(@Param("start") Integer start, @Param("count") Integer count);
    int selectCountCategory();

    int updateCategory(Category c);

    int deleteCategory(Integer id);
}
