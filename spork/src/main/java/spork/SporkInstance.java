package spork;

import spork.internal.SporkExtensionLoader;

/**
 * Static (shared) instance of Spork.
 */
public final class SporkInstance {
	private static final Spork INSTANCE;

	private SporkInstance() {
	}

	static {
		INSTANCE = new Spork();

		// Try to load known extensions
		SporkExtensionLoader.load(INSTANCE, "spork.inject.SporkInject");
		SporkExtensionLoader.load(INSTANCE, "spork.android.SporkAndroid");
	}

	/**
	 * Get the shared instance of Spork.
	 */
	public static Spork get() {
		return INSTANCE;
	}

	/**
	 * Binds all annotations for a specific object.
	 *
	 * @param object the object to bind
	 * @param parameters an optional array of non-null module instances
	 */
	public static void bind(Object object, Object... parameters) {
		INSTANCE.bind(object, parameters);
	}
}
