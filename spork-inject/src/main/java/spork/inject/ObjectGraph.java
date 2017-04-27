package spork.inject;

import spork.SporkInstance;

/**
 * Public interface for object graphs.
 */
public interface ObjectGraph {

	/**
	 * A shortcut to Spork.bind(Object, ObjectGraph).
	 * This binds all known annotations for the shared SporkInstance instance including spork-inject.
	 * @param object the object to bind
	 */
	void inject(Object object);

	/**
	 * A shortcut to spork.getBinder().bind(Object, ObjectGraph)
	 * This binds all known annotations for the given SporkInstance instance including spork-inject.
	 * @param object the object to bind
	 */
	void inject(Object object, SporkInstance sporkInstance);
}
