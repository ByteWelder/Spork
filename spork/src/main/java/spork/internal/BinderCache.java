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

import spork.Binder;
import spork.BinderRegistry;
import spork.FieldBinder;
import spork.MethodBinder;
import spork.TypeBinder;

/**
 * Holds the {@link Binder} instances for all known types.
 *
 * It extends {@link BinderRegistry} because newly registered binders (after initialization)
 * must be registered to the cache so the cache can be updated.
 */
@ThreadSafe
class BinderCache implements BinderRegistry {
	@SuppressWarnings("PMD.UseConcurrentHashMap") // we need to synchronize on combined get & put
	private final Map<Class<?>, List<Binder>> classBinderMap = new HashMap<>();
	private final BinderManager binderManager;

	BinderCache(BinderManager binderManager) {
		this.binderManager = binderManager;
	}

	/**
	 * Gets the ClassBinderCache for a given type.
	 * If the ClassBinderCache isn't available yet, it will be created and returned.
	 *
	 * @param type the class for the cached type
	 * @return the cache
	 */
	List<Binder> getBinders(Class<?> type) {
		synchronized (classBinderMap) {
			List<Binder> binderList = classBinderMap.get(type);

			if (binderList == null) {
				binderList = getBindersInternal(type);
				classBinderMap.put(type, binderList);
			}

			return binderList;
		}
	}

	@Override
	public void register(FieldBinder<?> fieldBinder) {
		synchronized (classBinderMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<Binder>> entry : classBinderMap.entrySet()) {
				gatherBinders(entry.getClass(), fieldBinder, entry.getValue());
			}
		}
	}

	@Override
	public void register(MethodBinder<?> methodBinder) {
		synchronized (classBinderMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<Binder>> entry : classBinderMap.entrySet()) {
				gatherBinders(entry.getClass(), methodBinder, entry.getValue());
			}
		}
	}


	@Override
	public void register(TypeBinder<?> typeBinder) {
		synchronized (classBinderMap) {
			// Run through existing cached types and updated them
			for (Map.Entry<Class<?>, List<Binder>> entry : classBinderMap.entrySet()) {
				gatherBinders(entry.getClass(), typeBinder, entry.getValue());
			}
		}
	}

	/**
	 * Find all Binder instances for the given type.
	 * Each Type/Field/Method annotation that is found in the type declaration
	 * will result in a corresponding Binder instance.
	 *
	 * @param classObject the class to create a cache for
	 * @return the list of cached binders
	 */
	private List<Binder> getBindersInternal(Class<?> classObject) {
		ArrayList<Binder> binders = new ArrayList<>();

		for (TypeBinder<?> typeBinder : binderManager.getTypeBinders()) {
			gatherBinders(classObject, typeBinder, binders);
		}

		for (FieldBinder<?> fieldBinder : binderManager.getFieldBinders()) {
			gatherBinders(classObject, fieldBinder, binders);
		}

		for (MethodBinder<?> methodBinder : binderManager.getMethodBinders()) {
			gatherBinders(classObject, methodBinder, binders);
		}

		binders.trimToSize();

		return binders;
	}


	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotated Field
	 * @param fieldBinder      the field binder to cache annotated fields for
	 * @param binders          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void gatherBinders(Class<?> annotatedType, final FieldBinder<AnnotationType> fieldBinder, List<Binder> binders) {
		for (final Field field : annotatedType.getDeclaredFields()) {
			final AnnotationType annotation = field.getAnnotation(fieldBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			binders.add(new Binder() {
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
	 * @param binders          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void gatherBinders(Class<?> annotatedType, final MethodBinder<AnnotationType> methodBinder, List<Binder> binders) {
		for (final Method method : annotatedType.getDeclaredMethods()) {
			final AnnotationType annotation = method.getAnnotation(methodBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			binders.add(new Binder() {
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
	 * @param binders          the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	private <AnnotationType extends Annotation> void gatherBinders(final Class<?> annotatedType, final TypeBinder<AnnotationType> typeBinder, List<Binder> binders) {
		final @Nullable AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

		if (annotation != null) {
			binders.add(new Binder() {
				@Override
				public void bind(Object object, Object... parameters) {
					typeBinder.bind(object, annotation, annotatedType, parameters);
				}
			});
		}
	}
}
