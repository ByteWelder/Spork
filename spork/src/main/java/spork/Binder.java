package spork;

/**
 * Binds annotations for a specified instance.
 */
public interface Binder {
	/**
	 * Bind all annotations for an object instance on all levels of inheritance.
	 *
	 * @param object the instance to bind annotations for
	 * @param parameters optional parameters(e.g. an ObjectGraph for the spork-inject module)
	 */
	void bind(Object object, Object... parameters);
}