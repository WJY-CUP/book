package com.wjy.test.daoTest;

import com.wjy.dao.OrderDao;
import com.wjy.dao.impl.OrderDaoImpl;
import com.wjy.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class OrderDaoTest {

    private OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void savaOrder() {
//        userId必须为t_user中有的，否则会出错
        orderDao.saveOrder(new Order("123456789", new Date(), new BigDecimal(120), 0, 1));
    }

    @Test
    public void changeOrderStatus() {
        orderDao.changeOrderStatus("16121033598608", 1);

    }

    @Test
    public void queryOrders() {
        List<Order> orders = orderDao.queryOrders();
        orders.forEach(System.out::println);
    }

    @Test
    public void queryOrdersByUserId() {
        List<Order> orders = orderDao.queryOrdersByUserId(7);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}