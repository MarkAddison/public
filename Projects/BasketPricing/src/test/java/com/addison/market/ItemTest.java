package com.addison.market;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mark on 26/06/2016.
 */
public class ItemTest {

    @Test
    public void ItemConstruction() {
        Item item = new Item("Banana", 2, BigDecimal.valueOf(0.10));
        assertNotNull(item);
        assertEquals(item.getName(), "Banana");
        assertEquals(item.getQuantity(), 2);
        assertEquals(0, item.getPrice().compareTo(BigDecimal.valueOf(0.10)));
    }
}
