package spork;

/**
 * Allows manipulation of a SporkInstance instance with (for example) new binders.
 */
public interface SporkExtension {

	/**
	 * Manipulate a SporkInstance instance with (for example) new binders.
	 * @param spork the SporkInstance instance
	 */
	void initialize(SporkInstance spork);
}
