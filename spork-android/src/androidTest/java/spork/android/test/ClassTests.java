package spork.android.test;

import org.junit.Test;

import spork.android.internal.utils.Views;

import static org.hamcrest.MatcherAssert.assertThat;
import static spork.matchers.Matchers.utilityClass;

public class ClassTests {

    @Test
    public void test() throws Exception {
        // .utils
        assertThat(Views.class, utilityClass());
    }
}
