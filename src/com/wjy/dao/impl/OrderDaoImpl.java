package com.wjy.dao.impl;

import com.wjy.dao.OrderDao;
import com.wjy.pojo.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 15:27 2021/1/28
 * @E-mail: 15611562852@163.com
 */

@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao{

    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) VALUES(?,?,?,?,?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }

    @Override
    public int changeOrderStatus(String orderId, Integer status) {
        String sql = "update t_order set `status`=? where order_id=?";
        return update(sql,status,orderId);
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId from t_order";
        return queryForList(Order.class, sql);
    }

    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        String sql = "select `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId from t_order where user_id=?";
        return queryForList(Order.class, sql, userId);
    }
}
