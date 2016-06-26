package com.addison.market;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Mark on 26/06/2016.
 */
public class App {
    static final String POUND = "\u00A3";
    static DecimalFormat df = new DecimalFormat("0.00###");

    public static void main(String[] args) {
        Basket theBasket = new Basket();
        theBasket.addItem("Banana", 2, BigDecimal.valueOf(0.09));
        theBasket.addItem("Banana", 2, BigDecimal.valueOf(0.10));
        theBasket.addItem("Banana", 6, BigDecimal.valueOf(0.09));
        System.out.println(POUND + " " + df.format(theBasket.totalPrice()));
    }
}
