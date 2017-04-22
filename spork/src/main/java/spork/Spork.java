package spork;

import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.SporkExtensionLoader;

/**
 * Provides static access to the methods of a shared instance of SporkInstance.
 */
public final class Spork {
	private static final SporkInstance INSTANCE = new SporkInstance();

	private Spork() {
	}

	static {
		// Load known extensions (if present)
		SporkExtensionLoader.load(INSTANCE, "spork.inject.SporkInject");
		SporkExtensionLoader.load(INSTANCE, "spork.android.SporkAndroid");
	}

	/**
	 * Binds all annotations for a specific object.
	 *
	 * @param object the object to bind
	 * @param parameters optional parameters
	 */
	public static void bind(Object object, Object... parameters) {
		INSTANCE.bind(object, parameters);
	}

	/**
	 * Register a new FieldBinder.
	 * Must be called before the first bind() is called.
	 */
	public static void register(FieldBinder<?> binder) {
		INSTANCE.register(binder);
	}

	/**
	 * Register a new MethodBinder.
	 * Must be called before the first bind() is called.
	 */
	public static void register(MethodBinder<?> binder) {
		INSTANCE.register(binder);
	}

	/**
	 * Register a new TypeBinder.
	 * Must be called before the first bind() is called.
	 */
	public static void register(TypeBinder<?> binder) {
		INSTANCE.register(binder);
	}
}
