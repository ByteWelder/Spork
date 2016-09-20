package io.github.sporklibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.internal.Binder;
import io.github.sporklibrary.internal.BinderCache;
import io.github.sporklibrary.internal.BinderCacheImpl;
import io.github.sporklibrary.internal.BinderImpl;
import io.github.sporklibrary.internal.BinderManager;
import io.github.sporklibrary.internal.BinderManagerImpl;
import io.github.sporklibrary.internal.ModuleManager;
import io.github.sporklibrary.internal.inject.InjectFieldBinder;

/**
 * Main class to access Spork functionality.
 */
public final class Spork {

	private Spork() {
    }

	private static @Nullable BinderRegistry binderRegistry;
	private static @Nullable Binder binder;
	private static @Nullable ModuleManager moduleManager;

	/**
     * Bind a single object with all relevant instances.
     *
     * @param object the object to bind into
     */
    public static void bind(Object object) {
		if (binder == null) {
			initialize();
		}

        binder.bind(object);
    }

    /**
     * Bind a single object with all relevant instances.
     *
     * @param object the object to bind into
     * @param modules specifies 1 or more modules
     */
    public static void bind(Object object, Object... modules) {
		if (binder == null) {
			initialize();
		}

        binder.bind(object, modules);
    }

    public static BinderRegistry getBinderRegistry() {
		if (binderRegistry == null) {
			initialize();
		}

        return binderRegistry;
    }

	public static ModuleManager getModuleManager() {
		if (moduleManager == null) {
			initialize();
		}

		return moduleManager;
	}

    /**
     * Tries to register the SporkAndroid bindings if the library is present in the classpath.
     *
     * @param binderRegistry the binder registry to register bindings to
     */
    private static void tryInitializeSporkAndroidBindings(BinderRegistry binderRegistry) {
        try {
            Class<?> sporkAndroidClass = Class.forName("io.github.sporklibrary.android.SporkAndroid");
            Method initializeMethod = sporkAndroidClass.getDeclaredMethod("initialize", BinderRegistry.class);
            initializeMethod.invoke(null, binderRegistry);
        } catch (ClassNotFoundException e) {
			// no-op
        } catch (NoSuchMethodException e) {
            System.out.println("Spork: Spork for Android found, but initialize method is not present");
        } catch (InvocationTargetException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of InvocationTargetException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Spork: Spork for Android found, but initialization failed because of IllegalAccessException: " + e.getMessage());
        }
    }

	private static synchronized void initialize() {
		// create all instances
		BinderManager binderManager = new BinderManagerImpl();
		final BinderCache binderCache = new BinderCacheImpl(binderManager);
		binderRegistry = binderManager;
		binder = new BinderImpl(binderCache);
		moduleManager = new ModuleManager();

		// ensure the cache is updated when a new type is registered
		binderManager.addRegistrationListener(new BinderManager.RegistrationListener() {
			@Override
			public void onRegisterFieldBinder(FieldBinder<?> fieldBinder) {
				binderCache.cache(fieldBinder);
			}

			@Override
			public void onRegisterMethodBinder(MethodBinder<?> methodBinder) {
				binderCache.cache(methodBinder);
			}

			@Override
			public void onRegisterTypeBinder(TypeBinder<?> typeBinder) {
				binderCache.cache(typeBinder);
			}
		});

		// registration must happen after cache is created and listening for registrations
		binderManager.register(new InjectFieldBinder());

		// Try auto-binding Spork for Android through reflection
		tryInitializeSporkAndroidBindings(binderManager);
	}
}
