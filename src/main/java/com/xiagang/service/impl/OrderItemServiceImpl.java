package com.xiagang.service.impl;

import com.xiagang.bean.Product;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao;

    @Autowired
    public OrderItemServiceImpl(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    @Override
    public int getSaleCount(Product p) {
        return orderItemDao.selectSaleCount(p);
    }
}
