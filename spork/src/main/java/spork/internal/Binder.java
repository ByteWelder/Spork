package spork.internal;

import java.util.List;

/**
 * The main logic for binding instances.
 *
 * It uses a {@link BindActionProvider} to bind objects.
 */
public class Binder {
	private final BindActionProvider bindActionProvider;

	public Binder(BindActionProvider bindActionProvider) {
		this.bindActionProvider = bindActionProvider;
	}

	public void bind(Object object, Object... parameters) {
		Class<?> objectClass = object.getClass();

		// Go through all levels of inheritance and find the BindAction for each class
		while (objectClass != Object.class) {
			List<BindAction> bindActions = bindActionProvider.getBindActions(objectClass);
			bind(object, bindActions, parameters);
			objectClass = objectClass.getSuperclass();
		}
	}

	/**
	 * Bind all annotations for an object instance for one specific class at a single level of inheritance.
	 *
	 * @param object  the instance to bind annotations for
	 * @param bindActions a list of BindAction instances to call
	 * @param parameters optional parameters
	 */
	private void bind(Object object, List<BindAction> bindActions, Object... parameters) {
		for (BindAction bindAction : bindActions) {
			bindAction.bind(object, parameters);
		}
	}
}
