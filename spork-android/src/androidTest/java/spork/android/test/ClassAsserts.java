package spork.android.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class ClassAsserts {
    /**
     * Based on http://stackoverflow.com/a/10872497/3848666
     *
     * @param classObject the class
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void assertUtilityClassWellDefined(final Class<?> classObject)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        assertTrue("class must be final", Modifier.isFinal(classObject.getModifiers()));
        assertEquals("There must be only one constructor", 1,
                classObject.getDeclaredConstructors().length);

        final Constructor<?> constructor = classObject.getDeclaredConstructor();

        if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is not private");
        }

        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);

        for (final Method method : classObject.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(classObject)) {
                fail("there exists a non-static method:" + method);
            }
        }
    }
}
