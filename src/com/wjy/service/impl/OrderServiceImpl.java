package com.wjy.service.impl;

import com.wjy.dao.BookDao;
import com.wjy.dao.OrderDao;
import com.wjy.dao.OrderItemDao;
import com.wjy.dao.impl.BookDaoImpl;
import com.wjy.dao.impl.OrderDaoImpl;
import com.wjy.dao.impl.OrderItemDaoImpl;
import com.wjy.pojo.*;
import com.wjy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 9:54 2021/1/31
 * @E-mail: 15611562852@163.com
 */
@Service
public class OrderServiceImpl implements OrderService {

    // private OrderDao orderDao = new OrderDaoImpl();
    // private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    // private BookDao bookDao = new BookDaoImpl();

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private BookDao bookDao;

    @Override


    public String createOrder(Cart cart, Integer userId) {

        // 订单号 === 唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        // 创建一个订单对象
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);

        // 保存订单
        orderDao.saveOrder(order);

        Map<Integer, CartItem> items = cart.getItems();

        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem> item : items.entrySet()) {
            // 获取购物车中的每一个商品项
            CartItem value = item.getValue();
            // 转换为每一个订单项
            OrderItem orderItem = new OrderItem(null, value.getName(), value.getCount(), value.getPrice(), value.getTotalPrice(), orderId);
            // 保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            // 更新库存和销量
            Book book = bookDao.queryBookById(value.getId());
            book.setSales( book.getSales() + value.getCount() );
            book.setStock( book.getStock() - value.getCount() );
            bookDao.updateBook(book);
        }

        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> showAllOrders() {
        return orderDao.queryOrders();
    }

    @Override
    public List<Order> showMyOrders(Integer userId) {
        return orderDao.queryOrdersByUserId(userId);
    }

    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        return orderItemDao.queryOrderItemsByOrderId(orderId);
    }

    @Override
    public int receiveOrder(String orderId) {
        return orderDao.changeOrderStatus(orderId, 2);
    }

    // 管理员发货，修改订单状态为1
    public int sendOrder(String orderId) {
        return orderDao.changeOrderStatus(orderId, 1);
    }



}
