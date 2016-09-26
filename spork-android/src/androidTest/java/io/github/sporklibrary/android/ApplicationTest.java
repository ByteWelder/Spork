package io.github.sporklibrary.android;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApplicationTest {

    @Test
    public void test() {
        assertNotNull(Application.getInstance().getTestDimension());
        assertEquals("test", Application.getInstance().getTestString());
    }
}
