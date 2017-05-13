package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import spork.exceptions.BindFailed;
import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;

/**
 * Holds the {@link BindAction} instances for all known types.
 *
 * Calling the register() methods updates the cache.
 */
public final class BindActionProvider {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // we need to synchronize on combined get & put
	private final BindActionCache bindActionCache = new BindActionCache();
	private final Catalog catalog;
	private final BindActionCache.Factory factory;

	public BindActionProvider(Catalog catalog, BindActionCache.Factory factory) {
		this.catalog = catalog;
		this.factory = factory;
	}

	public BindActionProvider(Catalog catalog) {
		this.catalog = catalog;
		this.factory = new BindActionCache.Factory() {
			@Override
			public List<BindAction> create(Class<?> type) {
				return createBindActions(type);
			}
		};
	}

	/**
	 * Gets the {@link BindAction} instances for the specified type.
	 */
	List<BindAction> getBindActions(Class<?> type) {
		return bindActionCache.getOrCreate(type, factory);
	}

	// region Creating BindAction instances

	/**
	 * Create a list of all BindAction instances for the given type.
	 * Each Type/Field/Method annotation that is found in the type declaration
	 * will result in a corresponding BindAction instance.
	 *
	 * @param classObject the class to create a cache for
	 * @return the list of cached binders
	 */
	private List<BindAction> createBindActions(Class<?> classObject) {
		ArrayList<BindAction> bindActions = new ArrayList<>();

		for (TypeBinder<?> typeBinder : catalog.getTypeBinders()) {
			createBindActions(classObject, typeBinder, bindActions);
		}

		for (FieldBinder<?> fieldBinder : catalog.getFieldBinders()) {
			createBindActions(classObject, fieldBinder, bindActions);
		}

		for (MethodBinder<?> methodBinder : catalog.getMethodBinders()) {
			createBindActions(classObject, methodBinder, bindActions);
		}

		bindActions.trimToSize();

		return bindActions;
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotated Field
	 * @param fieldBinder      the field binder to cache annotated fields for
	 * @param bindActions          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void createBindActions(
			Class<?> annotatedType,
			final FieldBinder<AnnotationType> fieldBinder,
			List<BindAction> bindActions) {

		for (final Field field : annotatedType.getDeclaredFields()) {
			final AnnotationType annotation = field.getAnnotation(fieldBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) throws BindFailed {
					fieldBinder.bind(object, annotation, field, parameters);
				}
			});
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotated Method
	 * @param methodBinder     the method binder to cache annotated methods for
	 * @param bindActions          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void createBindActions(
			Class<?> annotatedType,
			final MethodBinder<AnnotationType> methodBinder,
			List<BindAction> bindActions) {

		for (final Method method : annotatedType.getDeclaredMethods()) {
			final AnnotationType annotation = method.getAnnotation(methodBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) throws BindFailed {
					methodBinder.bind(object, annotation, method, parameters);
				}
			});
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotation
	 * @param typeBinder       the type binder to cache annotated types for
	 * @param bindActions          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void createBindActions(
			final Class<?> annotatedType,
			final TypeBinder<AnnotationType> typeBinder,
			List<BindAction> bindActions) {

		final AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

		if (annotation != null) {
			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) throws BindFailed {
					typeBinder.bind(object, annotation, annotatedType, parameters);
				}
			});
		}
	}

	// endregion
}
