package com.addison.market;

import java.math.BigDecimal;

/**
 * Created by Mark on 26/06/2016.
 */
public class Item {
    String name;
    int quantity;
    BigDecimal price;

    Item(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
