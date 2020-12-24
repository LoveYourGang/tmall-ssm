package com.xiagang.controller;

import com.xiagang.bean.*;
import com.xiagang.comparator.*;
import com.xiagang.service.*;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller("/fore")
public class ForeController {
    private UserService userService;
    private CategoryService categoryService;
    private ProductService productService;
    private PropertyValueService propertyValueService;
    private ReviewService reviewService;
    private OrderItemService orderItemService;

    @Autowired
    public ForeController(UserService userService,
                          CategoryService categoryService,
                          ProductService productService,
                          PropertyValueService propertyValueService,
                          ReviewService reviewService,
                          OrderItemService orderItemService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.propertyValueService = propertyValueService;
        this.reviewService = reviewService;
        this.orderItemService = orderItemService;
    }

    @RequestMapping("/home.do")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        List<Category> cs = categoryService.getCategories();
        mv.addObject("cs", cs);
        mv.setViewName("home.jsp");
        return mv;
    }

    @RequestMapping("/register.do")
    public ModelAndView register(String name, String password) {
        ModelAndView mv = new ModelAndView();
        String page = "register.jsp";
        if(password == null || password.length() < 6) {
            String msg = "至少输入6位数密码";
            page = "forward:register.jsp";
            mv.addObject("msg", msg);
            mv.setViewName(page);
            return mv;
        }
        if(name != null && userService.registerCheck(name) == 0) {
            User user = new User(name, password);
            int res = userService.register(user);
            if(res > 0) {
                page = "redirect:registerSuccess.jsp";
            } else {
                page = "redirect:register.jsp";
            }
        }
        mv.setViewName(page);
        return mv;
    }

    @RequestMapping("/registerCheck.do")
    @ResponseBody
    public String registerCheck(String name) {
        String msg;
        int flag = userService.registerCheck(name);
        if(flag == -1) {
            msg = "<font color='red'>用户名不合法(不能为空或有空格)</font>";
        } else if(flag == 1) {
            msg = "<font color='red'>用户名已存在</font>";
        } else {
            msg = "<font color='green'>用户名可以使用</font>";
        }
        return msg;
    }

    @RequestMapping("/login.do")
    public ModelAndView login(HttpServletRequest request, String name, String password) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        String msg, page;
        if(name != null && password != null) {
            boolean flag = userService.login(session, name, password);
            if(flag) {
                page = "redirect:home.do";
                mv.setViewName(page);
            } else {
                page = "login.jsp";
                msg = "账号或者密码错误";
                mv.addObject("msg", msg);
                mv.setViewName(page);
            }
        }
        return mv;
    }

    @RequestMapping("/ajaxLogin.do")
    @ResponseBody
    public String ajaxLogin(HttpServletRequest request, String name, String password) {
        HttpSession session = request.getSession();
        String msg;
        if(userService.loginCheck(session)) {
            msg = "success";
        } else {
            msg = "fail";
            if(userService.login(session, name, password))
                msg = "success";
        }
        return msg;
    }

    @RequestMapping("/checkLogin.do")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) {
        if(userService.loginCheck(request.getSession())) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(userService.loginCheck(session)) {
            userService.logout(session);
        }
        return "redirect:home.do";
    }

    @RequestMapping("/product.do")
    public ModelAndView product(Integer pid) {
        ModelAndView mv = new ModelAndView();
        Product p = productService.getProduct(pid);
        List<PropertyValue> pvs = propertyValueService.getPropertyValue(p);
        List<Review> reviews = reviewService.getReviews(p);
        mv.addObject("p", p);
        mv.addObject("pvs", pvs);
        mv.addObject("reviews", reviews);
        mv.setViewName("product.jsp");

        return mv;
    }

    @RequestMapping("/category.do")
    public ModelAndView category(Integer cid, String sort) {
        ModelAndView mv = new ModelAndView();
        Category c = categoryService.getCategory(cid);
        if(sort != null) {
            switch(sort) {
                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleComparator());
                    break;
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
            }
        }
        mv.addObject("c", c);
        Page page = new Page(0, 10);
        page.setTotal(c.getProducts().size());
        page.setParam("&cid=" + cid);
        mv.addObject("page", page);
        mv.setViewName("category.jsp");

        return mv;
    }

    @RequestMapping("/search.do")
    public ModelAndView search(String keyword) {
        ModelAndView mv = new ModelAndView();
        List<Product> ps = productService.searchProducts(keyword);
        mv.addObject("ps", ps);
        mv.setViewName("searchResult.jsp");
        return mv;
    }

    @RequestMapping("/buyone.do")
    public String buyone(HttpSession session, Integer pid, Integer num) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            int oiid = 0;
            OrderItem oi = orderItemService.getOrderItem(user.getId(), -1, pid);
            if(oi != null) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.modifyOrderItem(oi);
            } else {
                oi = new OrderItem();
                oi.setUser(user);
                oi.setProduct(productService.getProduct(pid));
                oi.setNumber(num);
                orderItemService.addOrderItem(oi);
                oiid = oi.getId();
            }
            return "redirect:fore/buy.do?oiid=" + oiid;
        }
        return "login.jsp";
    }

    @RequestMapping("/buy.do")
    public ModelAndView buy(HttpSession session, Integer[] oiid) {
        ModelAndView mv = new ModelAndView();
        float total = 0;
        List<OrderItem> ois = new ArrayList<>(oiid.length);
        for(Integer id: oiid) {
            OrderItem oi = orderItemService.getOrderItem(id);
            if(oi != null) {
                total += oi.getProduct().getPromotePrice() * oi.getNumber();
                ois.add(oi);
            }
        }
        session.setAttribute("ois", ois);
        mv.addObject("total", total);
        mv.setViewName("buy.jsp");

        return mv;
    }

    @RequestMapping("/addCart.do")
    @ResponseBody
    public String addCart(HttpSession session, Integer pid, Integer num) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            OrderItem oi = orderItemService.getOrderItem(user.getId(), -1, pid);
            if(oi != null) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.modifyOrderItem(oi);
            } else {
                oi = new OrderItem();
                oi.setUser(user);
                oi.setProduct(productService.getProduct(pid));
                oi.setNumber(num);
                orderItemService.addOrderItem(oi);
                int cartTotalItemNumber = (int) session.getAttribute("cartTotalItemNumber");
                session.setAttribute("cartTotalItemNumber", ++cartTotalItemNumber);
            }
            return "success";
        }
        return "fail";
    }

    public ModelAndView cart(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if(user != null) {
            List<OrderItem> ois = orderItemService.getCart(user);
            mv.addObject("ois", ois);
            mv.setViewName("cart.jsp");
        } else {
            mv.setViewName("redirect:login.jsp");
        }
        return mv;
    }
}
