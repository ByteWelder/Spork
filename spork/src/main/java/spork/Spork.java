package spork;

import spork.internal.SporkExtensionLoader;

/**
 * Holds a shared instance of SporkInstance.
 * This instance tries to automatically intialise known SporkExtension implementations.
 */
public final class Spork {
	private static final SporkInstance INSTANCE;

	private Spork() {
	}

	static {
		INSTANCE = new SporkInstance();

		// Load known extensions (if present)
		SporkExtensionLoader.load(INSTANCE, "spork.inject.SporkInject");
		SporkExtensionLoader.load(INSTANCE, "spork.android.SporkAndroid");
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
