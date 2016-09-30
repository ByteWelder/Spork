package io.github.sporklibrary;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.Binder;
import io.github.sporklibrary.interfaces.BinderRegistry;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.interfaces.MethodBinder;
import io.github.sporklibrary.interfaces.TypeBinder;
import io.github.sporklibrary.internal.BinderCache;
import io.github.sporklibrary.internal.BinderCacheImpl;
import io.github.sporklibrary.internal.BinderImpl;
import io.github.sporklibrary.internal.BinderManager;
import io.github.sporklibrary.internal.BinderManagerImpl;
import io.github.sporklibrary.internal.inject.InjectFieldBinder;

/**
 * Main class to access Spork functionality.
 */
public class Spork {
	private final BinderRegistry binderRegistry;
	private final Binder binder;

	private static @Nullable Spork sharedInstance;

	/**
	 * Creates a new instances of Spork with its own BinderManager, Binder and BinderCache.
	 * Spork.sharedInstance() should be used for normal usage.
	 */
	public Spork() {
		// create all instances
		BinderManager binderManager = new BinderManagerImpl();
		final BinderCache binderCache = new BinderCacheImpl(binderManager);

		binderRegistry = binderManager;
		binder = new BinderImpl(binderCache);

		// ensure the cache is updated when a new type is registered
		binderManager.addRegistrationListener(new BinderManager.RegistrationListener() {
			@Override
			public void onRegisterFieldBinder(FieldBinder<?> fieldBinder) {
				binderCache.register(fieldBinder);
			}

			@Override
			public void onRegisterMethodBinder(MethodBinder<?> methodBinder) {
				binderCache.register(methodBinder);
			}

			@Override
			public void onRegisterTypeBinder(TypeBinder<?> typeBinder) {
				binderCache.register(typeBinder);
			}
		});

		// registration must happen after cache is created and listening for registrations
		binderManager.register(new InjectFieldBinder());

		initializeSporkExtension("io.github.sporklibrary.android.SporkExtension");
		initializeSporkExtension("io.github.sporklibrary.android.support.SporkExtension");
	}

	/**
	 * Try to initialize a SporkExtension.
	 * @param sporkExtensionClassName the SporkExtension class name
	 */
	private void initializeSporkExtension(String sporkExtensionClassName) {
		try {
			Class<?> sporkExtensionClass = Class.forName(sporkExtensionClassName);
			Object sporkExtensionObject = sporkExtensionClass.newInstance();
			if (sporkExtensionObject instanceof SporkExtension) {
				SporkExtension sporkExtension = (SporkExtension)sporkExtensionObject;
				sporkExtension.initialize(this);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("Spork: extension " + sporkExtensionClassName + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Spork: extension " + sporkExtensionClassName + "found, but failed to create instance: " + e.getMessage());
		}
	}

	/**
	 * @return the interface for binding
	 */
	public Binder getBinder() {
		return binder;
	}

	public BinderRegistry getBinderRegistry() {
		return binderRegistry;
	}

	// region static methods

	/**
	 * @return a shared instance of Spork (creates one if one hasn't been created yet)
	 */
	public static Spork sharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = new Spork();
		}

		return sharedInstance;
	}

	/**
	 * A shortcut to Spork.sharedInstance().getBinder().bind(object, modules)
	 * @param object the object to bind
	 * @param modules an optional array of non-null module instances
	 */
	public static void bind(Object object, @Nullable Object... modules) {
		sharedInstance().getBinder().bind(object, modules);
	}

	// endregion
}
