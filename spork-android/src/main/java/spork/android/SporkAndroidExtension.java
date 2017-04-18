package spork.android;

/**
 * Allows manipulation of a SporkAndroid instance with (for example) new context/view providers.
 */
public interface SporkAndroidExtension {
	/**
	 * Manipulate a SporkAndroid instance with (for example) new context/view providers.
	 * @param sporkAndroid the SporkAndroid instance
	 */
	void initialize(SporkAndroid sporkAndroid);
}
