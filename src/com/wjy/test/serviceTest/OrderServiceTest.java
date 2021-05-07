package com.wjy.test.serviceTest;

import com.wjy.pojo.*;
import com.wjy.service.BookService;
import com.wjy.service.OrderService;
import com.wjy.service.impl.BookServiceImpl;
import com.wjy.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {

    private OrderService orderService = new OrderServiceImpl();
    private BookService bookService = new BookServiceImpl();

    @Test
    public void createOrder() {

        Cart cart = new Cart();

        Book book = bookService.queryBookById(1);
        cart.addItem(new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice()));
        Book book1 = bookService.queryBookById(2);
        cart.addItem(new CartItem(book1.getId(), book1.getName(), 1, book1.getPrice(), book1.getPrice()));

        orderService.createOrder(cart, 1);
    }

    @Test
    public void sendOrder() {
        orderService.sendOrder("16121515357078");
        System.out.println(orderService.showMyOrders(8));
    }

    @Test
    public void receiveOrder() {
        orderService.receiveOrder("16121515357078");
        System.out.println(orderService.showMyOrders(8));
    }


    @Test
    public void queryOrders() {
        List<Order> orders = orderService.showAllOrders();
        orders.forEach(System.out::println);
    }

    @Test
    public void showMyOrders() {
        List<Order> orders = orderService.showAllOrders();
        orders.forEach(System.out::println);
    }

    @Test
    public void showOrderDetail() {
        List<OrderItem> orderItems = orderService.showOrderDetail("16121515357078");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
    }

}