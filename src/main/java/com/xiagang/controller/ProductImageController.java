package com.xiagang.controller;

import com.xiagang.bean.Product;
import com.xiagang.bean.ProductImage;
import com.xiagang.service.ProductImageService;
import com.xiagang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productImage")
public class ProductImageController {
    private ProductService productService;
    private ProductImageService piService;

    @Autowired
    public ProductImageController(ProductService productService, ProductImageService piService) {
        this.productService = productService;
        this.piService = piService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(Integer pid) {
        ModelAndView mv = new ModelAndView();
        Product p = productService.getProduct(pid);
        List<ProductImage> pisSingle = p.getProductSingleImages();
        List<ProductImage> pisDetail = p.getProductDetailImages();

        mv.addObject("p", p);
        mv.addObject("pisSingle", pisSingle);
        mv.addObject("pisDetail", pisDetail);
        mv.setViewName("admin/listProductImage.jsp");
        return mv;
    }

    @RequestMapping("/add.do")
    public String add(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        piService.addProductImage(request, params);
        return "redirect:list.do?pid=" + params.get("pid");
    }

    @RequestMapping("/delete.do")
    public String delete(HttpServletRequest request, Integer id) {
        ProductImage pi = piService.getProductImage(id);
        piService.deleteProductImage(request, id);
        return "redirect:list.do?pid=" + pi.getProduct().getId();
    }
}
