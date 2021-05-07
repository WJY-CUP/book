package com.wjy.test;

import com.wjy.pojo.Cart;
import com.wjy.pojo.CartItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class CartTest {

    Cart cart = new Cart();



    @Test
    public void addItem() {

        cart.addItem(new CartItem(1,"java入门",1, new BigDecimal(70), new BigDecimal(70)));
        cart.addItem(new CartItem(1,"java入门",1, new BigDecimal(70), new BigDecimal(70)));
        cart.addItem(new CartItem(2,"C++入门",1, new BigDecimal(70), new BigDecimal(70)));
        Map<Integer, CartItem> items = cart.getItems();

        for (Map.Entry<Integer, CartItem> itemEntry : items.entrySet()) {
            System.out.println(itemEntry);
        }
    }

    @Test
    public void deleteItem() {
        System.out.println("删除前");
        addItem();
        cart.deleteItem(1);
        System.out.println("删除后");
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> itemEntry : items.entrySet()) {
            System.out.println(itemEntry);
        }

    }

    @Test
    public void clear() {
        System.out.println("清空前");
        addItem();
        cart.clear();
        System.out.println("清空后");

        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> itemEntry : items.entrySet()) {
            System.out.println(itemEntry);
        }

    }

    @Test
    public void updateCount() {
        System.out.println("修改前");
        addItem();
        cart.updateCount(1,3);
        System.out.println("修改后");

        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> itemEntry : items.entrySet()) {
            System.out.println(itemEntry);
        }



    }
}