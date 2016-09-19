package io.github.sporklibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.internal.binders.InjectFieldBinder;

/**
 * Main class to access Spork functionality.
 */
public final class Spork {

    private Spork() {
    }

	private static @Nullable BinderManager binderManager;

	private static @Nullable
	io.github.sporklibrary.internal.ModuleManager moduleManager;

	/**
     * Bind a single object with all relevant instances.
     *
     * @param object the object to bind into
     */
    public static void bind(Object object) {
        getBinderManager().bind(object);
    }

    /**
     * Bind a single object with all relevant instances.
     *
     * @param object the object to bind into
     * @param modules specifies 1 or more modules
     */
    public static void bind(Object object, Object... modules) {
        getBinderManager().bind(object, modules);
    }

    public static BinderManager getBinderManager() {
		if (binderManager == null) {
			intialize();
		}

        return binderManager;
    }

	public static io.github.sporklibrary.internal.ModuleManager getModuleManager() {
		if (moduleManager == null) {
			intialize();
		}

		return moduleManager;
	}

    /**
     * Tries to register the SporkAndroid bindings if the library is present in the classpath.
     *
     * @param binderManager the binder manager to register bindings to
     */
    private static void tryInitializeSporkAndroidBindings(BinderManager binderManager) {
        try {
            Class<?> sporkAndroidClass = Class.forName("io.github.sporklibrary.android.SporkAndroid");
            Method initializeMethod = sporkAndroidClass.getDeclaredMethod("initialize", BinderManager.class);
            initializeMethod.invoke(null, binderManager);
            System.out.println("Spork: BinderManager created without Spork for Android");
        } catch (ClassNotFoundException e) {
            System.out.println("Spork: BinderManager created with Spork for Android");
        } catch (NoSuchMethodException e) {
            System.out.println("Spork: Spork for Android found, but initialize method is not present");
        } catch (InvocationTargetException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of InvocationTargetException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of IllegalAccessException: " + e.getMessage());
        }
    }

	private static void intialize() {
		binderManager = new BinderManager();
		binderManager.register(new InjectFieldBinder());

		moduleManager = new io.github.sporklibrary.internal.ModuleManager();

		// Try auto-binding Spork for Android through reflection
		tryInitializeSporkAndroidBindings(binderManager);
	}
}
