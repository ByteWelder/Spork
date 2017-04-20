package spork.internal;

import spork.Spork;
import spork.SporkExtension;

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
	public static void load(Spork spork, String className) {
		try {
			Class<?> extensionClass = Class.forName(className);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof SporkExtension) {
				SporkExtension extension = (SporkExtension) extensionObject;
				extension.initialize(spork);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("Spork: extension " + className + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Spork: extension " + className + "found, but failed to create instance: " + e.getMessage());
		}
	}
}
