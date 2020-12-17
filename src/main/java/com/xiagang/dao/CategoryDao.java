package com.xiagang.dao;

import com.xiagang.bean.Category;

import java.util.List;

public interface CategoryDao {
    int insertCategory(Category c);

    Category selectCategoryById(Integer id);
    Category selectCategoryByName(String name);
    List<Category> selectAllCategories();
    List<Category> selectCategoryLimit(Integer start, Integer count);
    int selectCountCategory();

    int updateCategory(Category c);

    int deleteCategory(Integer id);
}
