package io.github.sporklibrary.test;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import io.github.sporklibrary.utils.Reflection;
import io.github.sporklibrary.utils.Views;

import static io.github.sporklibrary.test.ClassAsserts.assertUtilityClassWellDefined;

@SmallTest
public class ClassTests {

    @Test
    public void test() throws Exception {
        // .utils
        assertUtilityClassWellDefined(Views.class);
        assertUtilityClassWellDefined(Reflection.class);
    }
}
