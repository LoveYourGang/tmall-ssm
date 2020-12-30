package com.xiagang.service;

import com.xiagang.bean.Order;
import com.xiagang.bean.User;

import java.util.List;

public interface OrderService {
    int addOrder(Order order);

    Order getOrder(Integer id);
    List<Order> getOrder(User user);

    int payOrder(Order order);
    int confirmOrder(Order order);
    int finishOrder(Order order);

    int deleteOrder(Integer id);
}
