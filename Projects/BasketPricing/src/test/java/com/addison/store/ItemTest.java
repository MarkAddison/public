package com.addison.store;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mark on 25/06/2016.
 */
public class ItemTest {

    @Test
    public void addTwoItemsToCart() {
        Item item = new Item("Banana", 2);
        assertNotNull(item);
        assertEquals(item.getName(), "Banana");
        assertEquals(item.getQuantity(), 2);
    }
}
