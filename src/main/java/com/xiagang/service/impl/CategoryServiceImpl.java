package com.xiagang.service.impl;

import com.xiagang.bean.Category;
import com.xiagang.dao.CategoryDao;
import com.xiagang.service.CategoryService;
import com.xiagang.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private BaseService base;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, BaseService base) {
        this.categoryDao = categoryDao;
        this.base = base;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false)
    public int addCategory(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        InputStream is = base.parseUpload(request, params);

        String name= params.get("name");
        Category c = new Category();
        c.setName(name);
        int res = categoryDao.insertCategory(c);

        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");
        if(!base.uploadImage(is, file)) {
            res = -1;
        }
        return res;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> cs = categoryDao.selectAllCategories();
        for(Category c: cs) {
            base.fillCategory(c);
        }
        return cs;
    }

    @Override
    public List<Category> getCategoriesLimit(Integer start, Integer count) {
        return categoryDao.selectCategoryLimit(start, count);
    }

    @Override
    public int totalCategories() {
        return categoryDao.selectCountCategory();
    }

    @Override
    public Category getCategory(Integer id) {
        Category c = categoryDao.selectCategoryById(id);
        base.fillCategory(c);
        return c;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false)
    public int modifyCategory(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        InputStream is = base.parseUpload(request, params);
        Category c = new Category();
        c.setId(Integer.parseInt(params.get("id")));
        c.setName(params.get("name"));

        int res = categoryDao.updateCategory(c);
        File  imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");

        if(!base.uploadImage(is, file)) {
            res = -1;
        }
        return res;
    }

    @Override
    public int deleteCategory(Integer id) {
        return categoryDao.deleteCategory(id);
    }

}
