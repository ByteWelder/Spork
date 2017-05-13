package spork;

import javax.annotation.Nullable;

import spork.exceptions.SporkRuntimeException;
import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.BindActionProvider;
import spork.internal.Binder;
import spork.internal.Catalog;

/**
 * Main Spork class that is used to bind objects and register new binders.
 */
public final class SporkInstance {
	private final Catalog catalog;
	@Nullable private Binder binder;

	// region Constructors

	public SporkInstance() {
		catalog = new Catalog();
	}

	/**
	 * Constructor for testing.
	 */
	SporkInstance(@Nullable Binder binder, Catalog catalog) {
		this.binder = binder;
		this.catalog = catalog;
	}

	// endregion

	// region Bind methods

	/**
	 * Binds all annotations for a specific object.
	 *
	 * @param object the object to bind
	 * @param parameters an optional array of non-null module instances
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	public void bind(Object object, Object... parameters) {
		if (binder == null) {
			BindActionProvider bindActionProvider = new BindActionProvider(catalog);
			binder = new Binder(bindActionProvider);
		}

		try {
			binder.bind(object, parameters);
		} catch (Exception caught) {
			// wrap into RuntimeException to clean up the stacktrace and for easy use of this method
			throw new SporkRuntimeException(caught);
		}
	}

	// endregion

	// region Binder registration methods

	/**
	 * Register a new FieldBinder.
	 * Must be called before the first bind() is called.
	 */
	public void register(FieldBinder<?> fieldBinder) {
		if (binder != null) {
			throw new IllegalStateException("Binders must be registered before the first bind() is called");
		}

		catalog.add(fieldBinder);
	}

	/**
	 * Register a new MethodBinder.
	 * Must be called before the first bind() is called.
	 */
	public void register(MethodBinder<?> methodBinder) {
		if (binder != null) {
			throw new IllegalStateException("Binders must be registered before the first bind() is called");
		}

		catalog.add(methodBinder);
	}

	/**
	 * Register a new TypeBinder.
	 * Must be called before the first bind() is called.
	 */
	public void register(TypeBinder<?> typeBinder) {
		if (binder != null) {
			throw new IllegalStateException("Binders must be registered before the first bind() is called");
		}

		catalog.add(typeBinder);
	}

	// endregion
}
