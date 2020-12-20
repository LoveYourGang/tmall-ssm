package com.xiagang;

import com.xiagang.bean.Property;
import com.xiagang.dao.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DaoTest {
    private ApplicationContext ctx;

    public DaoTest() {
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
    }

    @Test
    public void testOrderDao() throws IOException {
        OrderDao dao = (OrderDao) ctx.getBean("orderDao");
    }

    @Test
    public void testOrderItemDao() throws IOException {
        OrderItemDao dao = (OrderItemDao) ctx.getBean("orderItemDao");
    }

    @Test
    public void testProductDao() throws IOException {
        ProductDao dao = (ProductDao) ctx.getBean("productDao");
    }

    @Test
    public void testProductImageDao() throws IOException {
        ProductImageDao dao = (ProductImageDao) ctx.getBean("productImageDao");
    }

    @Test
    public void testPropertyDao() throws IOException {
        String[] names = ctx.getBeanDefinitionNames();
        for(String name: names) {
            System.out.println(name);
        }
        PropertyDao dao = (PropertyDao) ctx.getBean("propertyDao");
        Property pt = dao.selectPropertyById(100);
        System.out.println(pt);
    }
}
