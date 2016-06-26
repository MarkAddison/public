package com.addison.store;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Mark on 26/06/2016.
 */
public class Prices {
    private static Map<String, BigDecimal> prices;

    public Prices() {
    }

    public static BigDecimal getPrice(String name) {
        if (prices.containsKey(name))
            return prices.get(name);
        else
            throw new RuntimeException("No valid price held for " + name);
    }

    public void setPrices(Map<String, BigDecimal> prices) {
        Prices.prices = prices;
    }
}
