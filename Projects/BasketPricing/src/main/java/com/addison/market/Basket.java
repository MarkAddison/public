package com.addison.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 26/06/2016.
 */
public class Basket implements BasketService {
    public List<Item> basket = new ArrayList<>();

    public void addItem(String name, int quantity, BigDecimal price) {
        basket.add(new Item(name, quantity, price));
    }

    public BigDecimal totalPrice() {
        return basket.stream().map(item -> item.price.multiply(new BigDecimal(item.quantity))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
