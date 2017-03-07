package spork;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

public class BindExceptionTests {
    private static final String REASON = "test";

    public class TestClass {
        // Must be public to be easily accessible by test
        public final Object mObject;

        public TestClass(Object object) {
            mObject = object;
        }
    }

    @Test(expected = BindException.class)
    public void testThrow1() {
        throw new BindException(Nullable.class, getClass(), REASON);
    }

    @Test(expected = BindException.class)
    public void testThrow2() {
        throw new BindException(Nullable.class, getClass(), REASON, new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow3() {
        Field field = TestClass.class.getFields()[0];
        throw new BindException(Nullable.class, getClass(), field, REASON);
    }

    @Test(expected = BindException.class)
    public void testThrow4() {
        Field field = TestClass.class.getFields()[0];
        throw new BindException(Nullable.class, getClass(), field, REASON, new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow5() {
        Method method = TestClass.class.getMethods()[0];
        throw new BindException(Nullable.class, getClass(), method, REASON);
    }

    @Test(expected = BindException.class)
    public void testThrow6() {
        Method method = TestClass.class.getMethods()[0];
        throw new BindException(Nullable.class, String.class, method, REASON, new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow7() {
        throw new BindException(Nullable.class, REASON);
    }

    @Test(expected = BindException.class)
    public void testThrow8() {
        throw new BindException(Nullable.class, getClass(), String.class, REASON);
    }

    @Test(expected = BindException.class)
    public void testThrow9() {
        throw new BindException(Nullable.class, getClass(), String.class, REASON, new Exception());
    }

    @Test(expected = BindException.class)
    public void testThrow10() {
        throw new BindException(Nullable.class, REASON, new Exception());
    }
}
