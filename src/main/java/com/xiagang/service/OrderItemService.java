package com.xiagang.service;

import com.xiagang.bean.OrderItem;
import com.xiagang.bean.Product;
import com.xiagang.bean.User;

import java.util.List;

public interface OrderItemService {
    int addOrderItem(OrderItem oi);

    int getSaleCount(Product p);
    OrderItem getOrderItem(Integer id);
    OrderItem getOrderItem(Integer uid, Integer oid, Integer pid);
    List<OrderItem> getCart(User user);

    int modifyOrderItem(OrderItem oi);

    int deleteOrderItem(Integer id);
}
