package spork;

import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.BindActionCache;
import spork.internal.Binder;
import spork.internal.Registry;

/**
 * Main class to access Spork functionality.
 */
public class Spork {
	private final Registry registry;
	private final Binder binder;
	private final BindActionCache bindActionCache;

	/**
	 * Constructor that is used for testing.
	 */
	Spork(Registry registry, BindActionCache bindActionCache, Binder binder) {
		this.registry = registry;
		this.bindActionCache = bindActionCache;
		this.binder = binder;
	}

	public Spork() {
		registry = new Registry();
		bindActionCache = new BindActionCache(registry);
		binder = new Binder(bindActionCache);
	}

	// region static methods

	/**
	 * Binds all annotations for a specific object.
	 *
	 * @param object the object to bind
	 * @param parameters an optional array of non-null module instances
	 */
	public void bind(Object object, Object... parameters) {
		binder.bind(object, parameters);
	}

	public void register(spork.extension.FieldBinder<?> binder) {
		registry.register(binder);
		bindActionCache.register(binder);
	}

	public void register(MethodBinder<?> binder) {
		registry.register(binder);
		bindActionCache.register(binder);
	}

	public void register(TypeBinder<?> binder) {
		registry.register(binder);
		bindActionCache.register(binder);
	}

	// endregion
}
