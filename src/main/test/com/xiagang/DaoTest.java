package com.xiagang;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiagang.bean.*;
import com.xiagang.dao.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class DaoTest {
    private ApplicationContext ctx;

    public DaoTest() {
        String config = "applicationContext.xml";
        ctx = new ClassPathXmlApplicationContext(config);
    }

    @Test
    public void testOrderDao() {
        OrderDao dao = (OrderDao) ctx.getBean("orderDao");
    }

    @Test
    public void testOrderItemDao() {
        OrderItemDao dao = (OrderItemDao) ctx.getBean("orderItemDao");
    }

    @Test
    public void testProductDao() {
        ProductDao dao = (ProductDao) ctx.getBean("productDao");
    }

    @Test
    public void testProductImageDao() {
        ProductImageDao dao = (ProductImageDao) ctx.getBean("productImageDao");
    }

    @Test
    public void testPropertyDao() {
        PropertyDao dao = (PropertyDao) ctx.getBean("propertyDao");
        Property pt = dao.selectPropertyById(32);
        System.out.println(pt);
    }

    @Test
    public void testPropertyValueDao() {
        PropertyValueDao dao = (PropertyValueDao) ctx.getBean("propertyValueDao");

        PropertyValue pv = new PropertyValue();
        pv.setId(14105);
        pv.setValue("黑色大腿袜有吊带");
        int res = dao.updatePropertyValue(pv);
        System.out.println(res);
    }

    @Test
    public void testReviewDao() {
        ReviewDao dao = (ReviewDao) ctx.getBean("reviewDao");
        Review r = new Review();
        r.setId(11);
        r.setContent("***");
        int rs = dao.updateReview(r);
        System.out.println(rs);
    }
}
