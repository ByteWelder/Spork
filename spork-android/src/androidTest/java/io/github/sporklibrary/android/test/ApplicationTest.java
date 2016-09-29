package io.github.sporklibrary.android.test;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ApplicationTest {

    @Test
    public void test() {
        assertNotNull(Application.getInstance().getTestDimension());
        assertNotNull(Application.getInstance().getComponent());
    }
}
