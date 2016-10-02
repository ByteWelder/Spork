package spork.interfaces;

import spork.Spork;

/**
 * Allows manipulation of a Spork instance with (for example) new binders.
 */
public interface SporkExtension {

	/**
	 * Manipulate a Spork instance with (for example) new binders.
	 * @param spork the Spork instance
	 */
	void initialize(Spork spork);
}
