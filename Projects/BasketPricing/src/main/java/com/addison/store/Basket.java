package com.addison.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 26/06/2016.
 */
public class Basket implements BasketService {
    public List<Item> cart = new ArrayList<>();

    public void addItem(String name, int quantity) {
        cart.add(new Item(name, quantity));
    }


    public BigDecimal totalPrice() {
        return cart.stream().map(item -> Prices.getPrice(item.name).multiply(new java.math.BigDecimal(item.quantity))).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
