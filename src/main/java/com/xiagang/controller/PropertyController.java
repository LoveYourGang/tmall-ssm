package com.xiagang.controller;

import com.xiagang.bean.Category;
import com.xiagang.bean.Property;
import com.xiagang.service.CategoryService;
import com.xiagang.service.PropertyService;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/property")
public class PropertyController {
    private CategoryService categoryService;
    private PropertyService propertyService;

    @Autowired
    public PropertyController(CategoryService categoryService, PropertyService propertyService) {
        this.categoryService = categoryService;
        this.propertyService = propertyService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(Integer cid, @RequestParam(value = "page.start",defaultValue = "0") Integer start) {
        ModelAndView mv = new ModelAndView();
        Category c = categoryService.getCategory(cid);
        List<Property> pts = propertyService.getProperty(c);
        Page page = new Page(start, 10);
        page.setTotal(pts.size());
        page.setParam("&cid=" + cid);

        mv.addObject("c", c);
        mv.addObject("pts", pts);
        mv.addObject("page", page);
        mv.addObject("path", "property/list.do");
        mv.setViewName("admin/listProperty.jsp");
        return mv;
    }

    @RequestMapping("/add.do")
    public String add(String name, Integer cid) {
        Category c = new Category();
        c.setId(cid);
        Property pt = new Property();
        pt.setName(name);
        pt.setCategory(c);
        propertyService.addProperty(pt);
        return "redirect:list.do?&cid=" + cid;
    }

    @RequestMapping("/delete.do")
    public String delete(Integer id) {
        Property pt = propertyService.getProperty(id);
        propertyService.deleteProperty(id);
        return "redirect:list.do?&cid=" + pt.getCategory().getId();
    }

    @RequestMapping("/edit.do")
    public ModelAndView edit(Integer id) {
        Property pt = propertyService.getProperty(id);
        return new ModelAndView("admin/editProperty.jsp", "pt", pt);
    }

    @RequestMapping("/modify.do")
    public String modify(Integer id, String name, Integer cid) {
        Property pt = propertyService.getProperty(id);
        pt.setName(name);
        propertyService.modifyProperty(pt);
        return "redirect:list.do?cid=" + cid;
    }
}
