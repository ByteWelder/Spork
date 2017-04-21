package spork.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;

/**
 * Holds the {@link BindAction} instances for all known types.
 *
 * Calling the register() methods updates the cache.
 */
@ThreadSafe
public class BindActionCache {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // we need to synchronize on combined get & put
	private final Map<Class<?>, List<BindAction>> bindActionMap = new HashMap<>();
	private final Registry registry;

	public BindActionCache(Registry registry) {
		this.registry = registry;
	}

	/**
	 * Gets the ClassBinderCache for a given type.
	 * If the ClassBinderCache isn't available yet, it will be created and returned.
	 *
	 * @param type the class for the cached type
	 * @return the cache
	 */
	List<BindAction> getBindActions(Class<?> type) {
		synchronized (bindActionMap) {
			List<BindAction> binderList = bindActionMap.get(type);

			// If no cache exists, create cache
			if (binderList == null) {
				binderList = collectBindActions(type);
				bindActionMap.put(type, binderList);
			}

			return binderList;
		}
	}

	// region Registration

	public void register(FieldBinder<?> binder) {
		synchronized (bindActionMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<BindAction>> entry : bindActionMap.entrySet()) {
				collectBindActions(entry.getClass(), binder, entry.getValue());
			}
		}
	}

	public void register(MethodBinder<?> binder) {
		synchronized (bindActionMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<BindAction>> entry : bindActionMap.entrySet()) {
				collectBindActions(entry.getClass(), binder, entry.getValue());
			}
		}
	}


	public void register(TypeBinder<?> binder) {
		synchronized (bindActionMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<BindAction>> entry : bindActionMap.entrySet()) {
				collectBindActions(entry.getClass(), binder, entry.getValue());
			}
		}
	}

	// endregion

	// region Collecting

	/**
	 * Find all Binder instances for the given type.
	 * Each Type/Field/Method annotation that is found in the type declaration
	 * will result in a corresponding Binder instance.
	 *
	 * @param classObject the class to create a cache for
	 * @return the list of cached binders
	 */
	private List<BindAction> collectBindActions(Class<?> classObject) {
		ArrayList<BindAction> bindActions = new ArrayList<>();

		for (TypeBinder<?> typeBinder : registry.getTypeBinders()) {
			collectBindActions(classObject, typeBinder, bindActions);
		}

		for (FieldBinder<?> fieldBinder : registry.getFieldBinders()) {
			collectBindActions(classObject, fieldBinder, bindActions);
		}

		for (MethodBinder<?> methodBinder : registry.getMethodBinders()) {
			collectBindActions(classObject, methodBinder, bindActions);
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
	private <AnnotationType extends Annotation> void collectBindActions(Class<?> annotatedType, final FieldBinder<AnnotationType> fieldBinder, List<BindAction> bindActions) {
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
	private <AnnotationType extends Annotation> void collectBindActions(Class<?> annotatedType, final MethodBinder<AnnotationType> methodBinder, List<BindAction> bindActions) {
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
	private <AnnotationType extends Annotation> void collectBindActions(final Class<?> annotatedType, final TypeBinder<AnnotationType> typeBinder, List<BindAction> bindActions) {
		final @Nullable AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

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
