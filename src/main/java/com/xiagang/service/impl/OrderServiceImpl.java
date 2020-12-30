package com.xiagang.service.impl;

import com.xiagang.bean.Order;
import com.xiagang.bean.User;
import com.xiagang.dao.OrderDao;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.service.BaseService;
import com.xiagang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private BaseService base;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, BaseService base) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.base = base;
    }

    @Override
    public int addOrder(Order order) {
        order.setCreateDate(new Date());
        order.setStatus(Order.waitPay);
        order.setOrderCode(createOrderCode(order));
        return orderDao.insertOrder(order);
    }

    @Override
    public Order getOrder(Integer id) {
        Order order = orderDao.selectOrderById(id);
        base.fillOrder(order);
        return order;
    }

    @Override
    public List<Order> getOrder(User user) {
        List<Order> orders = orderDao.selectOrderByUser(user);
        orders.forEach(base::fillOrder);
        return orders;
    }

    @Override
    public int payOrder(Order order) {
        order.setPayDate(new Date());
        order.setStatus(Order.waitDelivery);
        return orderDao.updateOrder(order);
    }

    @Override
    public int confirmOrder(Order order) {
        order.setConfirmDate(new Date());
        order.setStatus(Order.waitReview);
        return orderDao.updateOrder(order);
    }

    @Override
    public int finishOrder(Order order) {
        order.setStatus(Order.finish);
        return orderDao.updateOrder(order);
    }

    @Override
    public int deleteOrder(Integer id) {
        return orderDao.deleteOrder(id);
    }

    private String createOrderCode(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(order.getUser().getId());
        sb.append(order.getMobile());
        sb.append(order.getCreateDate().getTime());
        return sb.toString();
    }

//    private void fillOrder(Order order) {
//        List<OrderItem> ois = orderItemDao.selectOrderItemByOrder(order);
//        order.setOrderItems(ois);
//    }
}
