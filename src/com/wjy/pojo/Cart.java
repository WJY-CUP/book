package com.wjy.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 12:22 2021/1/25
 * @E-mail: 15611562852@163.com
 */

public class Cart {

    // 用LinkedHashMap方便查找，Integer为商品ID，且Map本身自带增删改查功能
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addItem(CartItem cartItem) {

        CartItem item = items.get(cartItem.getId());
        // 如果没有，新增商品
        if (item == null) {
            items.put(cartItem.getId(), cartItem);
        }
        else {
            // 如果购物车已有，数量加一即可
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }

    public void deleteItem(Integer id) {
        items.remove(id);
    }

    public void clear() {
        items.clear();
    }

    public void updateCount(Integer id, Integer count) {
        CartItem item = items.get(id);
//        如果购物车已有，数量加一即可
        if (item != null) {
            item.setCount(count);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }



    public Cart() {
    }


    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> item : items.entrySet()) {
            totalCount += item.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> item : items.entrySet()) {
            totalPrice = totalPrice.add(item.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    public Cart(Integer totalCount, BigDecimal totalPrice, Map<Integer, CartItem> items) {
        this.items = items;
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
