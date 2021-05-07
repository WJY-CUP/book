package com.wjy.service;

import com.wjy.pojo.Cart;
import com.wjy.pojo.Order;
import com.wjy.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    // 生成订单
    String createOrder(Cart cart, Integer userId);

    // 用户查看个人订单
    List<Order> showMyOrders(Integer userId);

    // 用户确认收货
    int receiveOrder(String orderId);

    // 用户查看订单详情
    List<OrderItem> showOrderDetail(String orderId);

    // 管理员查询全部订单
    List<Order> showAllOrders();

    // 管理员发货
    int sendOrder(String orderId);

}
