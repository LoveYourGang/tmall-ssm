package com.xiagang.controller;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;
import com.xiagang.bean.PropertyValue;
import com.xiagang.service.CategoryService;
import com.xiagang.service.ProductService;
import com.xiagang.service.PropertyValueService;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private PropertyValueService pvService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, PropertyValueService pvService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.pvService = pvService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(Integer cid, @RequestParam(value = "page.start", defaultValue = "0") Integer start) {
        ModelAndView mv = new ModelAndView();
        Page page = new Page(start, 10);

        Category c = categoryService.getCategory(cid);
        List<Product> ps = c.getProducts();
        page.setTotal(ps.size());
        page.setParam("&cid=" + cid);
        mv.addObject("c", c);
        mv.addObject("ps", ps);
        mv.addObject("page", page);
        mv.addObject("path", "product/list.do");
        mv.setViewName("admin/listProduct.jsp");

        return mv;
    }

    @RequestMapping("/add.do")
    public String add(Product p, Integer cid) {
        Category c = categoryService.getCategory(cid);
        p.setCategory(c);
        productService.addProduct(p);
        return "redirect:list.do?cid=" + cid;
    }

    @RequestMapping("/delete.do")
    public String delete(Integer id) {
        Category c = productService.getProduct(id).getCategory();
        productService.deleteProduct(id);
        return "redirect:list.do?cid=" + c.getId();
    }

    @RequestMapping("/edit.do")
    public ModelAndView edit(Integer id) {
        Product p = productService.getProduct(id);
        return new ModelAndView("admin/editProduct.jsp", "p", p);
    }

    @RequestMapping("/modify.do")
    public String modify(Product p, Integer cid) {
        productService.modifyProduct(p);
        return "redirect:list.do?cid=" + cid;
    }

    @RequestMapping("/editPropertyValue.do")
    public ModelAndView editPropertyValue(Integer id) {
        ModelAndView mv = new ModelAndView();
        Product p = productService.getProduct(id);
        List<PropertyValue> pvs = pvService.getPropertyValue(p);
        mv.addObject("p", p);
        mv.addObject("pvs", pvs);
        mv.setViewName("admin/editProductValue.jsp");
        return mv;
    }

    @RequestMapping("/modifyPropertyValue.do")
    @ResponseBody
    public String modifyPropertyValue(Integer pvid, String value) {
        PropertyValue pv = new PropertyValue();
        pv.setId(pvid);
        pv.setValue(value);
        if(pvService.modifyPropertyValue(pv) > 0)
            return "success";
        return "fail";
    }
}
