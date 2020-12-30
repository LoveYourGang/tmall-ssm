package com.xiagang;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;
import com.xiagang.dao.CategoryDao;
import com.xiagang.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ServiceTest {
    private ApplicationContext ctx;

    public ServiceTest() {
        String config = "applicationContext.xml";
        ctx = new ClassPathXmlApplicationContext(config);
    }

    @Test
    public void testProduct() {
        ProductService service = (ProductService) ctx.getBean("productService");
        List<Product> products = service.searchProducts("美女");
        //for(Product p: products) {
            System.out.println(products.get(0).getReviewCount());
        //}
    }
}
