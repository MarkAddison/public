package com.addison.store;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mark on 26/06/2016.
 */
public class BasketTest {
    Basket theBasket;
    Prices prices = new Prices();

    @Before
    public void setUp() {
        theBasket = new Basket();
        prices.setPrices(testPrices());
    }

    private Map<String, BigDecimal> testPrices() {
        return Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>("Apple", BigDecimal.valueOf(0.23)),
                new AbstractMap.SimpleEntry<>("Banana", BigDecimal.valueOf(0.10)),
                new AbstractMap.SimpleEntry<>("Lemon", BigDecimal.valueOf(0.35)),
                new AbstractMap.SimpleEntry<>("Orange", BigDecimal.valueOf(0.16)),
                new AbstractMap.SimpleEntry<>("Peach", BigDecimal.valueOf(0.45)))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
    }

    @After
    public void tearDown() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void PriceBasketOfBananas() {
        theBasket.addItem("Banana", 2);
        theBasket.addItem("Banana", 2);
        theBasket.addItem("Banana", 6);
        assertEquals(0, theBasket.totalPrice().compareTo(BigDecimal.valueOf(10.0).multiply(Prices.getPrice("Banana"))));
    }

    @Test
    public void PriceBasketOfMixedFruit() {
        theBasket.addItem("Apple", 2);
        theBasket.addItem("Banana", 4);
        theBasket.addItem("Lemon", 1);
        theBasket.addItem("Orange", 3);
        theBasket.addItem("Peach", 2);
        assertEquals(0, theBasket.totalPrice().compareTo(BigDecimal.valueOf(2.0).multiply(Prices.getPrice("Apple")).
                add(BigDecimal.valueOf(4.0).multiply(Prices.getPrice("Banana")).
                        add(BigDecimal.valueOf(1.0).multiply(Prices.getPrice("Lemon")).
                                add(BigDecimal.valueOf(3.0).multiply(Prices.getPrice("Orange")).
                                        add(BigDecimal.valueOf(2.0).multiply(Prices.getPrice("Peach"))))))));
    }
}
