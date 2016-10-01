package spork.test.exceptions;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.annotations.Inject;
import spork.annotations.Singleton;
import spork.exceptions.BindException;

public class BindExceptionTests {

    public class TestClass {
        // Must be public to be easily accessible by test
        public final Object mObject;

        public TestClass(Object object) {
            mObject = object;
        }
    }

    @Test(expected = BindException.class)
    public void testThrow1() {
        throw new BindException(Inject.class, getClass(), "reason");
    }

    @Test(expected = BindException.class)
    public void testThrow2() {
        throw new BindException(Inject.class, getClass(), "reason", new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow3() {
        Field field = TestClass.class.getFields()[0];
        throw new BindException(Inject.class, getClass(), field, "reason");
    }

    @Test(expected = BindException.class)
    public void testThrow4() {
        Field field = TestClass.class.getFields()[0];
        throw new BindException(Inject.class, getClass(), field, "reason", new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow5() {
        Method method = TestClass.class.getMethods()[0];
        throw new BindException(Inject.class, getClass(), method, "reason");
    }

    @Test(expected = BindException.class)
    public void testThrow6() {
        Method method = TestClass.class.getMethods()[0];
        throw new BindException(Inject.class, Singleton.class, method, "reason", new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow7() {
        throw new BindException(Inject.class, "reason");
    }
}
