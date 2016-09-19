package io.github.sporklibrary.test.methodbinding;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.internal.reflection.AnnotatedMethod;
import io.github.sporklibrary.internal.reflection.AnnotatedMethods;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MethodBindingTests {
    private BindMethodBinder bindMethodBinder;

    public static class MethodBinderParent {
        private int testCallCount = 0;

        public MethodBinderParent() {
            Spork.bind(this);
        }

        @BindMethod
        private void privateCallCountMethod() {
            testCallCount++;
        }

        @BindMethod
        public static int staticEchoMethod(int a) {
            return a;
        }

        public int getPrivateCallCount() {
            return testCallCount;
        }
    }

    @Before
    public void registerTestBinders() {
        bindMethodBinder = new BindMethodBinder();
        Spork.getBinderManager().register(bindMethodBinder);
    }

    @Test
    public void methodBinding() {
        assertEquals(bindMethodBinder.getMethodCount(), 0);

        new MethodBinderParent();

        assertEquals(bindMethodBinder.getMethodCount(), 2);
    }

    @Test
    public void invoke() throws NoSuchMethodException {
        MethodBinderParent object = new MethodBinderParent();

        Method method = MethodBinderParent.class.getDeclaredMethod("privateCallCountMethod");
        BindMethod annotation = method.getAnnotation(BindMethod.class);
        AnnotatedMethod<BindMethod> annotatedMethod = new AnnotatedMethod<>(annotation, method);

        // Testing AnnotatedMethod
        assertEquals(annotation, annotatedMethod.getAnnotation());
        assertEquals(method, annotatedMethod.getMethod());

        assertEquals(0, object.getPrivateCallCount());

        Object regularResult = AnnotatedMethods.invoke(annotatedMethod, object);
        assertNull(regularResult);

        assertEquals(1, object.getPrivateCallCount());

        Method staticMethod = MethodBinderParent.class.getMethod("staticEchoMethod", int.class);
        BindMethod staticMethodAnnotation = staticMethod.getAnnotation(BindMethod.class);
        AnnotatedMethod<BindMethod> staticAnnotatedMethod = new AnnotatedMethod<>(staticMethodAnnotation, staticMethod);

        assertEquals(123, MethodBinderParent.staticEchoMethod(123));

        Object staticResult = AnnotatedMethods.invoke(staticAnnotatedMethod, null, 123);
        assertNotNull(staticResult);
        assertEquals(123, staticResult);
    }
}
