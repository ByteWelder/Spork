package io.github.sporklibrary.test.fieldbinding;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldAndMethodBindingTests {
    private BindFieldOrMethodBinder testBinder;

    public static class BinderParent {

        @BindFieldOrMethod
        private Object mField;

        @BindFieldOrMethod
        public void test() {
        }

        @BindFieldOrMethod
        public static void testStatic() {
        }
    }

    @Before
    public void registerTestBinders() {
        testBinder = new BindFieldOrMethodBinder();
        Spork.getBinderManager().register((FieldBinder<BindFieldOrMethod>) testBinder);
        Spork.getBinderManager().register((MethodBinder<BindFieldOrMethod>) testBinder);
    }

    @Test
    public void methodBinding() {
        Assert.assertEquals(0, testBinder.getFieldBindCount());
        Assert.assertEquals(0, testBinder.getMethodBindCount());

        BinderParent object = new BinderParent();

        Spork.bind(object);

        Assert.assertEquals(1, testBinder.getFieldBindCount());
        Assert.assertEquals(2, testBinder.getMethodBindCount());
    }
}
