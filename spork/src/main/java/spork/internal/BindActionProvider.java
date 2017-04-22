package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;

/**
 * Holds the {@link BindAction} instances for all known types.
 *
 * Calling the register() methods updates the cache.
 */
public class BindActionProvider {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // we need to synchronize on combined get & put
	private final Map<Class<?>, List<BindAction>> bindActionMap = new HashMap<>();
	private final Catalog catalog;

	public BindActionProvider(Catalog catalog) {
		this.catalog = catalog;
	}

	/**
	 * Gets the {@link BindAction} instances for the specified type.
	 */
	List<BindAction> getBindActions(Class<?> type) {
		synchronized (bindActionMap) {
			List<BindAction> binderList = bindActionMap.get(type);

			// If no cache exists, create cache
			if (binderList == null) {
				binderList = createBindActions(type);
				bindActionMap.put(type, binderList);
			}

			return binderList;
		}
	}

	// region Creating BindAction instances

	/**
	 * Find all Binder instances for the given type.
	 * Each Type/Field/Method annotation that is found in the type declaration
	 * will result in a corresponding Binder instance.
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
	private <AnnotationType extends Annotation> void createBindActions(Class<?> annotatedType, final FieldBinder<AnnotationType> fieldBinder, List<BindAction> bindActions) {
		for (final Field field : annotatedType.getDeclaredFields()) {
			final AnnotationType annotation = field.getAnnotation(fieldBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) {
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
	private <AnnotationType extends Annotation> void createBindActions(Class<?> annotatedType, final MethodBinder<AnnotationType> methodBinder, List<BindAction> bindActions) {
		for (final Method method : annotatedType.getDeclaredMethods()) {
			final AnnotationType annotation = method.getAnnotation(methodBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) {
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
	private <AnnotationType extends Annotation> void createBindActions(final Class<?> annotatedType, final TypeBinder<AnnotationType> typeBinder, List<BindAction> bindActions) {
		final AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

		if (annotation != null) {
			bindActions.add(new BindAction() {
				@Override
				public void bind(Object object, Object... parameters) {
					typeBinder.bind(object, annotation, annotatedType, parameters);
				}
			});
		}
	}

	// endregion
}
