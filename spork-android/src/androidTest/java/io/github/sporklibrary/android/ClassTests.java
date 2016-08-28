package io.github.sporklibrary.android;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import io.github.sporklibrary.android.utils.Reflection;
import io.github.sporklibrary.android.utils.Views;

import static io.github.sporklibrary.android.ClassAsserts.assertUtilityClassWellDefined;

@SmallTest
public class ClassTests {

    @Test
    public void test() throws Exception {
        // .utils
        assertUtilityClassWellDefined(Views.class);
        assertUtilityClassWellDefined(Reflection.class);
    }
}
