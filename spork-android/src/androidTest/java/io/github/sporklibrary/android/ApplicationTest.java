package io.github.sporklibrary.android;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class ApplicationTest {

    @Test
    public void test() {
        assertNotNull(Application.getInstance().getTestDimension());
        assertNotNull(Application.getInstance().getComponent());
    }
}
