package spork.internal;

import spork.exceptions.BindFailed;

/**
 * An implementation of a binding action that binds an object instance for 1 specific field/method/type.
 */
interface BindAction {
	/**
	 * Bind the object.
	 *
	 * @param object the instance to bind annotations for
	 * @param parameters optional parameters(e.g. an ObjectGraph for the spork-inject module)
	 */
	void bind(Object object, Object... parameters) throws BindFailed;
}
