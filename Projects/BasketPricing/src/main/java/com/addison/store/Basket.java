package com.addison.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 26/06/2016.
 */
public class Basket implements BasketService {
    public List<Item> basket = new ArrayList<>();

    public void addItem(String name, int quantity) {
        basket.add(new Item(name, quantity));
    }


    public BigDecimal totalPrice() {
        return basket.stream().map(item -> Prices.getPrice(item.name).multiply(new java.math.BigDecimal(item.quantity))).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
