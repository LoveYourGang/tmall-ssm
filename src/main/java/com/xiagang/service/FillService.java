package com.xiagang.service;

import com.xiagang.bean.Category;
import com.xiagang.bean.Order;
import com.xiagang.bean.Product;
import org.springframework.stereotype.Service;

@Service
public interface FillService {
    void fillCategory(Category c);
    void fillOrder(Order order);
    void fillProduct(Product p);
}
