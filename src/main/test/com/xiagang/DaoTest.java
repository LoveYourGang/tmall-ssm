package com.xiagang;

import com.xiagang.bean.*;
import com.xiagang.dao.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoTest {
    private ApplicationContext ctx;

    public DaoTest() {
        String config = "applicationContext.xml";
        ctx = new ClassPathXmlApplicationContext(config);
    }

    @Test
    public void testOrderDao() {
        OrderDao dao = (OrderDao) ctx.getBean("orderDao");
        Order o = dao.selectOrderById(2);
        System.out.println(o.getUser().getName());
    }

    @Test
    public void testOrderItemDao() {
        OrderItemDao dao = (OrderItemDao) ctx.getBean("orderItemDao");
        Product p = new Product();
        p.setId(966);
        int s = dao.selectSaleCount(p);
        System.out.println("销量"+s);
    }

    @Test
    public void testProductDao() {
        ProductDao dao = (ProductDao) ctx.getBean("productDao");
        Product p = dao.selectProductById(966);
        System.out.println(p.getCategory().getName());
    }

    @Test
    public void testProductImageDao() {
        ProductImageDao dao = (ProductImageDao) ctx.getBean("productImageDao");
        ProductImage pi = dao.selectProductImageById(629);
        System.out.println(pi.getProduct().getName());
    }

    @Test
    public void testPropertyDao() {
        PropertyDao dao = (PropertyDao) ctx.getBean("propertyDao");
        Property pt = dao.selectPropertyById(32);
        System.out.println(pt.getCategory().getName());
    }

    @Test
    public void testPropertyValueDao() {
        PropertyValueDao dao = (PropertyValueDao) ctx.getBean("propertyValueDao");
        PropertyValue pv = dao.selectPropertyValueById(14105);
        System.out.println(pv.getProduct().getName() + "," + pv.getProperty().getName());
    }

    @Test
    public void testReviewDao() {
        ReviewDao dao = (ReviewDao) ctx.getBean("reviewDao");
        Product p = new Product();
        p.setId(965);
        int r = dao.selectReviewCount(p);
        System.out.println("评论" + r);
    }
}
