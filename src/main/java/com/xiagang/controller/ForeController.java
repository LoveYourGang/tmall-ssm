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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/fore")
public class ForeController {
    private UserService userService;
    private CategoryService categoryService;
    private ProductService productService;
    private PropertyValueService propertyValueService;
    private ReviewService reviewService;
    private OrderItemService orderItemService;
    private OrderService orderService;

    @Autowired
    public ForeController(UserService userService,
                          CategoryService categoryService,
                          ProductService productService,
                          PropertyValueService propertyValueService,
                          ReviewService reviewService,
                          OrderItemService orderItemService,
                          OrderService orderService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.propertyValueService = propertyValueService;
        this.reviewService = reviewService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
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
    public String register(String name, String password) {
        String page = "redirect:/register.jsp";
        if(userService.registerCheck(name) == 0) {
            User user = new User(name, password);
            int res = userService.register(user);
            if(res > 0) {
                page = "redirect:/registerSuccess.jsp";
            }
        }
        return page;
    }

    @RequestMapping(value = "/registerCheck.do", produces="text/plain;charset=UTF-8")
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
    public ModelAndView login(HttpSession session, String name, String password) {
        ModelAndView mv = new ModelAndView();
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
    public String ajaxLogin(HttpSession session, String name, String password) {
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
    public String checkLogin(HttpSession session) {
        if(userService.loginCheck(session)) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpSession session) {
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
            return "redirect:buy.do?oiid=" + oiid;
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

    @RequestMapping("/cart.do")
    public ModelAndView cart(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if(user != null) {
            List<OrderItem> ois = orderItemService.getCart(user);
            mv.addObject("ois", ois);
            mv.setViewName("cart.jsp");
        } else {
            mv.setViewName("redirect:/login.jsp");
        }
        return mv;
    }

    @RequestMapping("/changeOrderItem.do")
    @ResponseBody
    public String changeOrderItem(HttpSession session, Integer pid, Integer num) {
        User user = (User) session.getAttribute("user");
        if(user != null) {
            OrderItem oi = orderItemService.getOrderItem(user.getId(), -1, pid);
            oi.setNumber(num);
            if(orderItemService.modifyOrderItem(oi) > 0)
                return "success";
        }
        return "fail";
    }

    @RequestMapping("/deleteOrderItem.do")
    @ResponseBody
    public String deleteOrderItem(HttpSession session, Integer oiid) {
        User user = (User) session.getAttribute("user");
        if(user != null && orderItemService.deleteOrderItem(oiid) > 0) {
            int cartTotalItemNumber = (int) session.getAttribute("cartTotalItemNumber");
            session.setAttribute("cartTotalItemNumber", --cartTotalItemNumber);
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/createOrder.do")
    public String createOrder(HttpSession session, Order order) {
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = (int) session.getAttribute("cartTotalItemNumber");
        if(ois != null && user != null) {
            order.setUser(user);
            order.setOrderItems(ois);
            if(orderService.addOrder(order) > 0) {
                for(OrderItem oi: ois) {
                    oi.setOrder(order);
                    if(orderItemService.modifyOrderItem(oi) > 0)
                        cartTotalItemNumber--;
                }
            }
        }
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        return "redirect:alipay.do?oid=" + order.getId() + "&total=" + order.getTotal();
    }

    @RequestMapping("/alipay.do")
    public String alipay() {
        return "alipay.jsp";
    }

    @RequestMapping("/payed.do")
    public ModelAndView payed(Integer oid) {
        ModelAndView mv = new ModelAndView();
        Order order = orderService.getOrder(oid);
        mv.setViewName("redirect:fore/alipay.do?oid=" + order.getId() + "&total=" + order.getTotal());
        if(orderService.payOrder(order) > 0) {
            mv.addObject("o", order);
            mv.setViewName("payed.jsp");
        }
        return mv;
    }

    @RequestMapping("/bought.do")
    public ModelAndView bought(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        List<Order> os = orderService.getOrder(user);
        Collections.sort(os, new OrderDateComparator());
        mv.addObject("os", os);
        mv.setViewName("bought.jsp");
        return mv;
    }

    @RequestMapping("/deleteOrder.do")
    @ResponseBody
    public String deleteOrder(Integer oid) {
        if(orderService.deleteOrder(oid) > 0)
            return "success";
        return "fail";
    }

    @RequestMapping("/confirmPay.do")
    public ModelAndView confirmPay(Integer oid) {
        ModelAndView mv = new ModelAndView();
        Order order = orderService.getOrder(oid);
        mv.addObject("o", order);
        mv.setViewName("confirmPay.jsp");
        return mv;
    }

    @RequestMapping("orderConfirmed.do")
    public String orderConfirmed(Integer oid) {
        Order order = orderService.getOrder(oid);
        if(orderService.confirmOrder(order) > 0) {
            return "orderConfirmed.jsp";
        }
        return "redirect:bought.do";
    }

    @RequestMapping("/review.do")
    public ModelAndView review(Integer oid) {
        ModelAndView mv = new ModelAndView();
        Order order = orderService.getOrder(oid);
        List<OrderItem> ois = order.getOrderItems();
        List<List<Review>> reviews = new ArrayList<>(ois.size());
        for(OrderItem oi: ois) {
            Product p = oi.getProduct();
            List<Review> rs = reviewService.getReviews(p);
            reviews.add(rs);
        }
        mv.addObject("o", order);
        mv.addObject("ois", ois);
        mv.addObject("reviews", reviews);
        mv.setViewName("review.jsp");
        return mv;
    }

    @RequestMapping("/doReview.do")
    public String doReview(HttpSession session, Integer oid, Integer[] pid, String[] content) {
        User user = (User) session.getAttribute("user");
        Order order = orderService.getOrder(oid);
        boolean flag = true;
        for(int i=0; i < pid.length; i++) {
            Review r = new Review();
            Product product = productService.getProduct(pid[i]);
            String text = content[i];
            r.setUser(user);
            r.setProduct(product);
            r.setContent(text);
            flag = flag && (reviewService.addReview(r) > 0);
        }
        if(flag) {
            orderService.finishOrder(order);
        }
        return "fore/review.do?showonly=true&oid=" + oid;
    }
}
