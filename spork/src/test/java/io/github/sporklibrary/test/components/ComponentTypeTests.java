package io.github.sporklibrary.test.components;

import io.github.sporklibrary.test.components.shopping.Dairy;
import io.github.sporklibrary.test.components.shopping.FaultyShoppingCart;
import io.github.sporklibrary.test.components.shopping.IceCream;
import io.github.sporklibrary.test.components.shopping.ShoppingCart;
import io.github.sporklibrary.exceptions.BindException;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentTypeTests {

    @Test
    public void inheritanceTest() {
        ShoppingCart cart = new ShoppingCart();

        Dairy dairy = cart.getDairy();

        assertNotNull("Component injection", dairy);
        assertTrue("Component class override", dairy.getClass().equals(IceCream.class));
    }

    @Test(expected = BindException.class)
    public void typeMismatchTest() {
        new FaultyShoppingCart();
    }
}
