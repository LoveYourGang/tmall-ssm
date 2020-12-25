package com.xiagang.service.impl;

import com.xiagang.bean.OrderItem;
import com.xiagang.bean.Product;
import com.xiagang.bean.User;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.service.FillService;
import com.xiagang.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao;
    private FillService fill;

    @Autowired
    public OrderItemServiceImpl(OrderItemDao orderItemDao, FillService fill) {
        this.orderItemDao = orderItemDao;
        this.fill = fill;
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
        OrderItem oi = orderItemDao.selectOrderItemById(id);
        fill.fillProduct(oi.getProduct());
        fill.fillOrder(oi.getOrder());
        return oi;
    }

    @Override
    public OrderItem getOrderItem(Integer uid, Integer oid, Integer pid) {
        OrderItem oi = orderItemDao.selectOrderItemByUserOrderProduct(uid, oid, pid);
        fill.fillProduct(oi.getProduct());
        if(oi.getOrder() != null)
            fill.fillOrder(oi.getOrder());
        return oi;
    }

    @Override
    public List<OrderItem> getCart(User user) {
        List<OrderItem> ois = orderItemDao.selectUserCart(user);
        ois.forEach(oi -> fill.fillProduct(oi.getProduct())); //购物车无需填充订单
        return ois;
    }

    @Override
    public int modifyOrderItem(OrderItem oi) {
        return orderItemDao.updateOrderItem(oi);
    }

    @Override
    public int deleteOrderItem(Integer id) {
        return orderItemDao.deleteOrderItem(id);
    }

}
