package com.xiagang;

import com.xiagang.dao.OrderDao;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DaoTest {
    @Test
    public void testOrderDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        OrderDao dao = (OrderDao) ctx.getBean("orderDao");
    }

    @Test
    public void testOrderItemDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        OrderItemDao dao = (OrderItemDao) ctx.getBean("orderItemDao");
    }

    @Test
    public void testProductDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        ProductDao dao = (ProductDao) ctx.getBean("productDao");
    }

    @Test
    public void testProductImageDao() throws IOException {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        ProductImageDao dao = (ProductImageDao) ctx.getBean("productImageDao");
    }
}
