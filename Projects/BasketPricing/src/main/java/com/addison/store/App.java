package com.addison.store;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.text.DecimalFormat;

/**
 * Created by Mark on 26/06/2016.
 */
public class App {
    static final String POUND = "\u00A3";
    static DecimalFormat df = new DecimalFormat("0.00###");

    static private Map<String, BigDecimal> storePrices() {
        return Collections.unmodifiableMap(Stream.of(
                new java.util.AbstractMap.SimpleEntry<>("Apple", BigDecimal.valueOf(0.23)),
                new java.util.AbstractMap.SimpleEntry<>("Banana", BigDecimal.valueOf(0.10)),
                new java.util.AbstractMap.SimpleEntry<>("Lemon", BigDecimal.valueOf(0.35)),
                new java.util.AbstractMap.SimpleEntry<>("Orange", BigDecimal.valueOf(0.16)),
                new java.util.AbstractMap.SimpleEntry<>("Peach", BigDecimal.valueOf(0.45)))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
    }

    public static void main(String[] args) {
        Prices prices = new Prices();
        prices.setPrices(storePrices());

        Basket theBasket = new Basket();
        theBasket.addItem("Banana", 2);
        theBasket.addItem("Banana", 2);
        theBasket.addItem("Banana", 6);
        System.out.println(POUND + " " + df.format(theBasket.totalPrice()));
    }

}
