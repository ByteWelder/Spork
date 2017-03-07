package spork.android.test;

import org.junit.Test;

import spork.android.internal.utils.Views;
import spork.internal.Reflection;

import static spork.android.test.ClassAsserts.assertUtilityClassWellDefined;

public class ClassTests {

    @Test
    public void test() throws Exception {
        // .utils
        assertUtilityClassWellDefined(Views.class);
        assertUtilityClassWellDefined(Reflection.class);
    }
}
