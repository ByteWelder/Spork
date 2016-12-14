package spork;

import javax.annotation.Nullable;

import spork.interfaces.Binder;
import spork.interfaces.BinderRegistry;
import spork.interfaces.FieldBinder;
import spork.interfaces.MethodBinder;
import spork.interfaces.TypeBinder;
import spork.internal.BinderCache;
import spork.internal.BinderCacheImpl;
import spork.internal.BinderImpl;
import spork.internal.BinderManager;
import spork.internal.BinderManagerImpl;

/**
 * Main class to access Spork functionality.
 */
public class Spork {
	private final BinderRegistry binderRegistry;
	private final Binder binder;

	@Nullable
	private static Spork sharedInstance;

	/**
	 * Create a new instance of Spork with custom BinderRegistry and Binder implementations.
	 *
	 * @param binderRegistry .
	 * @param binder         .
	 */
	public Spork(BinderRegistry binderRegistry, Binder binder) {
		this.binderRegistry = binderRegistry;
		this.binder = binder;
	}

	/**
	 * Creates a new instances of Spork with the default BinderManager, Binder and BinderCache.
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
	}

	/**
	 * Try to initialize a SporkExtension.
	 *
	 * @param extensionClassName the SporkExtension class name
	 */
	private void initializeExtension(String extensionClassName) {
		try {
			Class<?> extensionClass = Class.forName(extensionClassName);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof spork.interfaces.SporkExtension) {
				spork.interfaces.SporkExtension extension = (spork.interfaces.SporkExtension) extensionObject;
				extension.initialize(this);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("Spork: extension " + extensionClassName + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Spork: extension " + extensionClassName + "found, but failed to create instance: " + e.getMessage());
		}
	}

	/**
	 * The global binding interface to bind annotations for object instances.
	 *
	 * @return the interface for binding
	 */
	public Binder getBinder() {
		return binder;
	}

	/**
	 * The registry interface to register new annotation binders.
	 *
	 * @return the BinderRegistry
	 */
	public BinderRegistry getBinderRegistry() {
		return binderRegistry;
	}

	// region static methods

	/**
	 * Get the shard instance of Spork.
	 * This instance tries to automatically initialize the spork-injection and spork-android libraries when they are available.
	 *
	 * @return a shared instance of Spork (creates one if one hasn't been created yet)
	 */
	public static synchronized Spork sharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = new Spork();

			sharedInstance.initializeExtension("spork.inject.SporkInject");
			sharedInstance.initializeExtension("spork.android.SporkAndroid");
		}

		return sharedInstance;
	}

	/**
	 * A shortcut to Spork.sharedInstance().getBinder().bind(object, modules)
	 *
	 * @param object  the object to bind
	 * @param modules an optional array of non-null module instances
	 */
	public static void bind(Object object, Object... modules) {
		sharedInstance().getBinder().bind(object, modules);
	}

	// endregion
}
