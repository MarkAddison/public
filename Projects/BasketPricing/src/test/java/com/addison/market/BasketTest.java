package com.addison.market;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mark on 26/06/2016.
 */
public class BasketTest {
    Basket theBasket;

    @Before
    public void setUp() {
        theBasket = new Basket();
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
    public void PriceEmptyBasket() {
        assertEquals(0, theBasket.totalPrice().compareTo(BigDecimal.valueOf(0.0)));
    }

    @Test
    public void PriceBasketOfBananas() {
        theBasket.addItem("Banana", 2, BigDecimal.valueOf(0.09)); // 0.18
        theBasket.addItem("Banana", 2, BigDecimal.valueOf(0.10)); // 0.20
        theBasket.addItem("Banana", 6, BigDecimal.valueOf(0.09)); // 0.54
        assertEquals(0, theBasket.totalPrice().compareTo(BigDecimal.valueOf(0.92)));
    }

    @Test
    public void PriceBasketOfMixedFruit() {
        theBasket.addItem("Apple", 2, BigDecimal.valueOf(0.25));  // 0.5
        theBasket.addItem("Banana", 4, BigDecimal.valueOf(0.09)); // 0.36
        theBasket.addItem("Lemon", 1, BigDecimal.valueOf(0.34));  // 0.34
        theBasket.addItem("Orange", 3, BigDecimal.valueOf(0.20)); // 0.60
        theBasket.addItem("Peach", 2, BigDecimal.valueOf(0.50));  // 1.00
        assertEquals(0, theBasket.totalPrice().compareTo(BigDecimal.valueOf(2.8)));
    }
}
