package com.addison.market;

import java.math.BigDecimal;

/**
 * Created by Mark on 26/06/2016.
 */
public interface BasketService {

    void addItem(String name, int quantity, BigDecimal price);

    BigDecimal totalPrice();

}