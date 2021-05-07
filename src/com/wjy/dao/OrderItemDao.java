package com.wjy.dao;

import com.wjy.pojo.OrderItem;

import java.util.List;

public interface OrderItemDao {

//    保存订单项
    int saveOrderItem(OrderItem orderItem);

//    根据订单号查询订单明细
    List<OrderItem> queryOrderItemsByOrderId(String orderId);

}
