package com.xiagang;

import com.xiagang.bean.Order;
import com.xiagang.bean.OrderItem;
import com.xiagang.bean.Product;
import com.xiagang.bean.User;
import com.xiagang.dao.OrderDao;
import com.xiagang.dao.OrderItemDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class DaoTest {
    @Test
    public void testOrderDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        OrderDao orderDao = (OrderDao) ctx.getBean("orderDao");
        User user = new User();
        user.setId(1);
        List<Order> orders = orderDao.selectOrderLimit(2,3);
        orders.forEach(s -> System.out.println(s.getUser().getName()));
    }

    @Test
    public void testOrderItemDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        OrderItemDao orderItemDao = (OrderItemDao) ctx.getBean("orderItemDao");

        //OrderItem item = orderItemDao.selectOrderItemByUserOrderProduct(1,2,430);
        //OrderItem item = orderItemDao.selectOrderItemById(10);
        OrderItem oi = new OrderItem();
        oi.setId(8);
        User user = new User();
        user.setId(0);
        oi.setUser(user);
        oi.setNumber(2);
        orderItemDao.updateOrderItem(oi);

//        for(OrderItem oi: items) {
//            System.out.println(oi.getId() + "," + oi.getUser().getId() + "," + oi.getOrder().getId() + "," + oi.getProduct().getId() + "," + oi.getNumber());
//        }
    }

    @Test
    public void testProductDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        Product p = (Product) ctx.getBean("productDao");
        System.out.println(p);
    }
}
