package com.xiagang.dao;

import com.xiagang.bean.Order;
import com.xiagang.bean.OrderItem;
import com.xiagang.bean.Product;
import com.xiagang.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemDao {
    int insertOrderItem(OrderItem oi);

    OrderItem selectOrderItemById(Integer id);
    OrderItem selectOrderItemByUserOrderProduct(@Param("uid") Integer uid, @Param("oid") Integer oid, @Param("pid") Integer pid);
    List<OrderItem> selectUserCart(User user);
    int selectUserCartCount(User user);
    List<OrderItem> selectOrderItemByOrder(Order order);
    int selectSaleCount(Product p);

    int updateOrderItem(OrderItem oi);

    int deleteOrderItem(Integer id);
}
