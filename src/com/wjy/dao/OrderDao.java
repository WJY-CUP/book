package com.wjy.dao;

import com.wjy.pojo.Order;

import java.util.List;

public interface OrderDao {

    int saveOrder(Order order);
    
    int changeOrderStatus(String orderId, Integer status);

    List<Order> queryOrders();

    List<Order> queryOrdersByUserId(Integer userId);

}
