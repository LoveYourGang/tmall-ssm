package com.xiagang.service.impl;

import com.xiagang.bean.OrderItem;
import com.xiagang.bean.Product;
import com.xiagang.bean.User;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao;

    @Autowired
    public OrderItemServiceImpl(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Override
    public int addOrderItem(OrderItem oi) {
        if(oi.getOrder() == null && oi.getNumber() > 0 && oi.getUser() != null && oi.getProduct() != null)
            return orderItemDao.insertOrderItem(oi);
        return 0;
    }

    @Override
    public int getSaleCount(Product p) {
        return orderItemDao.selectSaleCount(p);
    }

    @Override
    public OrderItem getOrderItem(Integer id) {
        return orderItemDao.selectOrderItemById(id);
    }

    @Override
    public OrderItem getOrderItem(Integer uid, Integer oid, Integer pid) {
        return orderItemDao.selectOrderItemByUserOrderProduct(uid, oid, pid);
    }

    @Override
    public List<OrderItem> getCart(User user) {
        return orderItemDao.selectUserCart(user);
    }

    @Override
    public int modifyOrderItem(OrderItem oi) {
        return orderItemDao.updateOrderItem(oi);
    }

}
