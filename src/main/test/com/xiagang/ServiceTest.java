package com.xiagang;

import com.xiagang.bean.Category;
import com.xiagang.bean.Product;
import com.xiagang.dao.CategoryDao;
import com.xiagang.service.CategoryService;
import com.xiagang.service.FillService;
import com.xiagang.service.ReviewService;
import com.xiagang.service.UserService;
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
    public void testFill() {
        CategoryService service = (CategoryService) ctx.getBean("categoryService");
        ctx.getBean("reviewService");
        List<Category> cs = service.getCategories();
        for(Category c: cs) {
            List<Product> ps = c.getProducts();
            for(Product p: ps) {
                System.out.println(p.getName() + "," + p.getSaleCount() + "," + p.getReviewCount());
            }
        }
    }
}
