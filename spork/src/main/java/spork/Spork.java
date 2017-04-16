package spork;

import javax.annotation.Nullable;

import spork.internal.BinderImpl;
import spork.internal.BinderManager;

/**
 * Main class to access Spork functionality.
 */
public class Spork {
	private final BinderRegistry binderRegistry;
	private final Binder binder;

	@Nullable
	private static final Spork SHARED;

	static {
		SHARED = new Spork();
		SHARED.initializeExtension("spork.inject.SporkInject");
		SHARED.initializeExtension("spork.android.SporkAndroid");
	}

	/**
	 * Main constructor.
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
	 * Spork.shared() should be used for normal usage.
	 */
	public Spork() {
		BinderManager binderManager = new BinderManager();
		binderRegistry = binderManager;
		binder = new BinderImpl(binderManager.getBinderCache());
	}

	/**
	 * Try to initialize a SporkExtension.
	 *
	 * @param className the SporkExtension class name
	 */
	@SuppressWarnings("PMD.EmptyCatchBlock")
	private void initializeExtension(String className) {
		try {
			Class<?> extensionClass = Class.forName(className);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof SporkExtension) {
				SporkExtension extension = (SporkExtension) extensionObject;
				extension.initialize(this);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("Spork: extension " + className + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Spork: extension " + className + "found, but failed to create instance: " + e.getMessage());
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
	 * This instance tries to automatically initialize the spork-inject and spork-android libraries when they are available.
	 *
	 * This method is not synchronized as the single-check idiom is applied here:
	 * If by chance on first call, 2 threads call this method, 2 instances of Spork might be created.
	 * This is not a problem as one of the 2 instances will just be GC'ed after usage.
	 *
	 * @return a shared instance of Spork (creates one if one hasn't been created yet)
	 */
	public static Spork shared() {
		return SHARED;
	}

	/**
	 * A shortcut to Spork.shared().getBinder().bind(object, modules)
	 *
	 * @param object the object to bind
	 * @param parameters an optional array of non-null module instances
	 */
	public static void bind(Object object, Object... parameters) {
		shared().getBinder().bind(object, parameters);
	}

	// endregion
}
