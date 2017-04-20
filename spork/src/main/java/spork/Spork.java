package spork;

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
	 * Main constructor.
	 * Create a new instance of Spork with custom Registry and Binder implementations.
	 *
	 * @param registry .
	 * @param binder         .
	 */
	public Spork(Registry registry, BindActionCache bindActionCache, Binder binder) {
		this.registry = registry;
		this.bindActionCache = bindActionCache;
		this.binder = binder;
	}

	/**
	 * Creates a new instances of Spork with the default AnnotationBinderManager, Binder and BindActionCache.
	 * Spork.shared() should be used for normal usage.
	 */
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

	public void register(FieldBinder<?> binder) {
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
