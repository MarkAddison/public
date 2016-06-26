package com.addison.store;

/**
 * Created by Mark on 25/06/2016.
 */
public class Item {
    String name;
    int quantity;

    Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
