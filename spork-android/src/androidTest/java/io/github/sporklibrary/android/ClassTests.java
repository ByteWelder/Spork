package io.github.sporklibrary.android;

import org.junit.Test;

import io.github.sporklibrary.android.internal.utils.Reflection;
import io.github.sporklibrary.android.internal.utils.Views;

import static io.github.sporklibrary.android.ClassAsserts.assertUtilityClassWellDefined;

public class ClassTests {

    @Test
    public void test() throws Exception {
        // .utils
        assertUtilityClassWellDefined(Views.class);
        assertUtilityClassWellDefined(Reflection.class);
    }
}
