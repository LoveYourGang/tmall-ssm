package com.xiagang.service.impl;

import com.xiagang.bean.Category;
import com.xiagang.dao.CategoryDao;
import com.xiagang.service.CategoryService;
import com.xiagang.service.FillService;
import com.xiagang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private FillService fill;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, FillService fill) {
        this.categoryDao = categoryDao;
        this.fill = fill;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> cs = categoryDao.selectAllCategories();
        for(Category c: cs) {
            fill.fillCategory(c);
        }
        return cs;
    }

    @Override
    public Category getCategory(Integer id) {
        Category c = categoryDao.selectCategoryById(id);
        fill.fillCategory(c);
        return c;
    }

//    private void fillCategory(Category c) {
//        List<Product> products = productService.getProducts(c);
//        c.setProducts(products);
//        int start, count=5;
//        List<List<Product>> productsByRow = new ArrayList<>();
//        for(start=0; start < products.size(); start += count) {
//            List<Product> ps = new ArrayList<>(count);
//            for(int i=0; i < count && start+i < products.size(); i++) {
//                ps.add(products.get(start+i));
//            }
//            productsByRow.add(ps);
//        }
//        c.setProductsByRow(productsByRow);
//    }
}
