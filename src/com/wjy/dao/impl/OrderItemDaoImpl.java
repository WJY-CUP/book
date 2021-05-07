package com.wjy.dao.impl;

import com.wjy.dao.OrderItemDao;
import com.wjy.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 15:34 2021/1/28
 * @E-mail: 15611562852@163.com
 */

@Repository
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) VALUES(?,?,?,?,?)";
        return update(sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        String sql = "select `order_id` orderId,`name`,`count`,`price`,`total_price` totalPrice from t_order_item where order_id=?";
        return queryForList(OrderItem.class,sql,orderId);
    }

}
