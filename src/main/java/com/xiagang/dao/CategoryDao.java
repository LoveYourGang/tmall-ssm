package com.xiagang.dao;

import com.xiagang.bean.Category;

public interface CategoryDao {
    int insertCategory(Category c);

    Category selectCategoryById(Integer id);
    Category selectCategoryByName(String name);

    int updateCategory(Category c);
}
