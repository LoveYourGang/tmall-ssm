package com.xiagang.controller;

import com.xiagang.bean.Order;
import com.xiagang.service.OrderService;
import com.xiagang.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/list.do")
    public ModelAndView list(@RequestParam(value = "page.start",defaultValue = "0") Integer start) {
        ModelAndView mv = new ModelAndView("admin/listOrder.jsp");
        Page page = new Page(start, 10);
        List<Order> os = orderService.getAllOrders();
        page.setTotal(os.size());
        mv.addObject("os", os);
        mv.addObject("page", page);
        mv.addObject("path", "order/list.do");
        return mv;
    }

    @RequestMapping("/delivery.do")
    public String delivery(Integer id) {
        orderService.deliveryOrder(orderService.getOrder(id));
        return "redirect:list.do";
    }
}
