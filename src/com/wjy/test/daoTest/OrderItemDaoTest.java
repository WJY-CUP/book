package com.wjy.test.daoTest;

import com.wjy.dao.OrderItemDao;
import com.wjy.dao.impl.OrderItemDaoImpl;
import com.wjy.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class OrderItemDaoTest {

    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null,"java  从入门到精通", 1,new BigDecimal(100),new
                BigDecimal(100),"123456789"));
        orderItemDao.saveOrderItem(new OrderItem(null,"javaScript  从入门到精通", 2,new
                BigDecimal(100),new BigDecimal(200),"123456789"));
        orderItemDao.saveOrderItem(new OrderItem(null,"Netty  入门", 1,new BigDecimal(100),new
                BigDecimal(100),"123456789"));
    }

    @Test
    public void queryOrderItemsByOrderId() {
        List<OrderItem> orderItems = orderItemDao.queryOrderItemsByOrderId("16121515357078");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }

    }
}