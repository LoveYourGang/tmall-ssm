package com.xiagang.dao;

import com.xiagang.bean.Order;
import com.xiagang.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDao {
    int insertOrder(Order order);

    Order selectOrderById(Integer id);
    Order selectOrderByOrderCode(String orderCode);
    List<Order> selectOrderByUser(User user);
    List<Order> selectOrderByUserAndStatus(@Param("user") User user, @Param("status") String status);
    List<Order> selectAllOrders();
    List<Order> selectOrderLimit(@Param("start") Integer start, @Param("count") Integer count);

    int updateOrder(Order order);
    int updateOrderStatus(@Param("id") Integer id, @Param("status") String status);

    int deleteOrder(Integer id);
}
