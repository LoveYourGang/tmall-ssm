package com.xiagang.service;

import com.xiagang.bean.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Integer id);
}
