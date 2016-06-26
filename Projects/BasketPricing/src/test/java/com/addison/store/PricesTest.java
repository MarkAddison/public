package com.addison.store;

import org.junit.Before;
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
public class PricesTest {
    Prices prices = new Prices();

    @Before
    public void setUp() {
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

    @Test
    public void ThePriceOfApples() {
        assertEquals(0, Prices.getPrice("Apple").compareTo(BigDecimal.valueOf(0.23)));
    }

    @Test
    public void ThePriceOfBananas() {
        assertEquals(0, Prices.getPrice("Banana").compareTo(BigDecimal.valueOf(0.10)));
    }

    @Test
    public void ThePriceOfLemons() {
        assertEquals(0, Prices.getPrice("Lemon").compareTo(BigDecimal.valueOf(0.35)));
    }

    @Test
    public void ThePriceOfOranges() {
        assertEquals(0, Prices.getPrice("Orange").compareTo(BigDecimal.valueOf(0.16)));
    }

    @Test
    public void ThePriceOfPeaches() {
        assertEquals(0, Prices.getPrice("Peach").compareTo(BigDecimal.valueOf(0.45)));
    }

    @Test(expected = RuntimeException.class)
    public void ThePriceOfAnUnknownItem() {
        Prices.getPrice("Unknown");
    }
}
