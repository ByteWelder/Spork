package spork.android.internal;

import spork.android.SporkAndroid;
import spork.android.SporkAndroidExtension;

public final class SporkAndroidExtensionLoader {

	private SporkAndroidExtensionLoader() {
	}

	/**
	 * Try to load a SporkAndroidExtension.
	 * Fails without throwing an exception if the extension is not present.
	 *
	 * @param extensionClassName the SporkAndroidExtension class name
	 */
	@SuppressWarnings("PMD.EmptyCatchBlock")
	public static void load(SporkAndroid sporkAndroid, String extensionClassName) {
		try {
			Class<?> extensionClass = Class.forName(extensionClassName);
			Object extensionObject = extensionClass.newInstance();
			if (extensionObject instanceof SporkAndroidExtension) {
				SporkAndroidExtension extension = (SporkAndroidExtension) extensionObject;
				extension.initialize(sporkAndroid);
			}
		} catch (ClassNotFoundException e) {
			// no-op
		} catch (IllegalAccessException e) {
			System.out.println("SporkAndroid: extension " + extensionClassName + "found, but initialization failed because of IllegalAccessException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("SporkAndroid: extension " + extensionClassName + "found, but failed to create instance: " + e.getMessage());
		}
	}
}
