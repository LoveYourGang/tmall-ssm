package com.xiagang.controller;

import com.xiagang.bean.Category;
import com.xiagang.service.CategoryService;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(@RequestParam(value = "page.start", defaultValue = "0") Integer start) {
        ModelAndView mv = new ModelAndView();
        Page page = new Page(start, 10);
        List<Category> cs = categoryService.getCategoriesLimit(page.getStart(), page.getCount());
        page.setTotal(categoryService.totalCategories());
        mv.addObject("thecs", cs);
        mv.addObject("page", page);
        mv.addObject("path", "category/list.do");
        mv.setViewName("admin/listCategory.jsp");
        return mv;
    }

    @RequestMapping("/add.do")
    public String add(HttpServletRequest request) {
        categoryService.addCategory(request);
        return "redirect:list.do";
    }

    @RequestMapping("/delete.do")
    public String delete(Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:list.do";
    }

    @RequestMapping("/edit.do")
    public ModelAndView edit(Integer id) {
        ModelAndView mv = new ModelAndView();
        Category c = categoryService.getCategory(id);
        mv.addObject("c", c);
        mv.setViewName("admin/editCategory.jsp");
        return mv;
    }

    @RequestMapping("/modify.do")
    public String modify(HttpServletRequest request) {
        categoryService.modifyCategory(request);
        return "redirect:list.do";
    }

}
