package io.github.sporklibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.component.ComponentFieldBinder;

/**
 * Main class to access Spork functionality.
 */
public final class Spork {

    private Spork() {
    }

    private static @Nullable BinderManager binderManager;

    /**
     * Bind a single object with all relevant instances.
     *
     * @param object the object to bind into
     */
    public static void bind(Object object) {
        getBinderManager().bind(object);
    }

    public static BinderManager getBinderManager() {
        // Only create an binder if the code is actually used
        if (binderManager == null) {
            binderManager = new BinderManager();
            binderManager.register(new ComponentFieldBinder());

            // Try auto-binding Spork for Android through reflection
            if (!tryInitializeSporkAndroidBindings("io.github.sporklibrary.SporkAndroid", binderManager)
                && !tryInitializeSporkAndroidBindings("io.github.sporklibrary.android.SporkAndroid", binderManager)) {
                System.out.println("Spork: BinderManager created without Spork for Android");
            } else {
                System.out.println("Spork: BinderManager created with Spork for Android");
            }
        }

        return binderManager;
    }

    /**
     * Tries to register the SporkAndroid bindings if the library is present in the classpath.
     *
     * @param binderManager the binder manager to register bindings to
     */
    private static boolean tryInitializeSporkAndroidBindings(String namespace, BinderManager binderManager) {
        try {
            Class<?> sporkAndroidClass = Class.forName(namespace);
            Method initializeMethod = sporkAndroidClass.getDeclaredMethod("initialize", BinderManager.class);
            initializeMethod.invoke(null, binderManager);
            return true;
        } catch (ClassNotFoundException e) {
            // ignore
        } catch (NoSuchMethodException e) {
            System.out.println("Spork: Spork for Android found, but initialize method is not present");
        } catch (InvocationTargetException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of InvocationTargetException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of IllegalAccessException: " + e.getMessage());
        }

        return false;
    }
}
