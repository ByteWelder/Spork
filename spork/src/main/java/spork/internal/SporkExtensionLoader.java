package spork.internal;

import spork.SporkInstance;
import spork.SporkExtension;
import spork.exceptions.ExtensionLoadingFailed;

/**
 * Utility class for {@link SporkExtension}
 */
public final class SporkExtensionLoader {

	private SporkExtensionLoader() {
	}

	/**
	 * Initialize a SporkExtension when it is available.
	 *
	 * @param className the SporkExtension class name
	 */
	@SuppressWarnings("PMD.EmptyCatchBlock")
	public static void load(SporkInstance spork, String className) {
		try {
			Class<?> extensionClass = Class.forName(className);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof SporkExtension) {
				SporkExtension extension = (SporkExtension) extensionObject;
				extension.initialize(spork);
			} else {
				throw new ExtensionLoadingFailed("Extension " + className + " does not implement " + SporkExtension.class);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			throw new ExtensionLoadingFailed("Failed to initialize " + className + " because of an IllegalAccessException", e);
		} catch (InstantiationException e) {
			throw new ExtensionLoadingFailed("Failed to create an instance of " + className, e);
		}
	}
}
