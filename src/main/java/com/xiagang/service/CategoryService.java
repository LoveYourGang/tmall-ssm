package com.xiagang.service;

import com.xiagang.bean.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CategoryService {
    int addCategory(HttpServletRequest request);

    List<Category> getCategories();
    List<Category> getCategoriesLimit(Integer start, Integer count);
    int totalCategories();
    Category getCategory(Integer id);

    int modifyCategory(HttpServletRequest request);

    int deleteCategory(Integer id);
}
