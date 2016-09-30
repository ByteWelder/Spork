package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.Binder;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.interfaces.MethodBinder;
import io.github.sporklibrary.interfaces.TypeBinder;

public class BinderCacheImpl implements BinderCache {
	private final Map<Class<?>, List<Binder>> classBinderCacheMap = new HashMap<>();
	private final BinderManager binderManager;

	public BinderCacheImpl(BinderManager binderManager) {
		this.binderManager = binderManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Binder> getBinders(Class<?> type) {
		List<Binder> binderList = classBinderCacheMap.get(type);

		if (binderList == null) {
			binderList = createCache(type);
			classBinderCacheMap.put(type, binderList);
		}

		return binderList;
	}

	@Override
	public void register(FieldBinder<?> fieldBinder) {
		// Run through existing cached types and updated them
		for (Map.Entry<Class<?>, List<Binder>> entry : classBinderCacheMap.entrySet()) {
			createCache(entry.getClass(), fieldBinder, entry.getValue());
		}
	}

	@Override
	public void register(MethodBinder<?> methodBinder) {
		// Run through existing cached types and updated them
		for (Map.Entry<Class<?>, List<Binder>> entry : classBinderCacheMap.entrySet()) {
			createCache(entry.getClass(), methodBinder, entry.getValue());
		}
	}

	@Override
	public void register(TypeBinder<?> typeBinder) {
		// Run through existing cached types and updated them
		for (Map.Entry<Class<?>, List<Binder>> entry : classBinderCacheMap.entrySet()) {
			createCache(entry.getClass(), typeBinder, entry.getValue());
		}
	}

	/**
	 * Allocated the cache for the specified class
	 *
	 * @param classObject the class to create a cache for
	 * @return the list of cached binders
	 */
	private List<Binder> createCache(Class<?> classObject) {
		ArrayList<Binder> binders = new ArrayList<>();

		for (TypeBinder<?> typeBinder : binderManager.getTypeBinders()) {
			createCache(classObject, typeBinder, binders);
		}

		for (FieldBinder<?> fieldBinder : binderManager.getFieldBinders()) {
			createCache(classObject, fieldBinder, binders);
		}

		for (MethodBinder<?> methodBinder : binderManager.getMethodBinders()) {
			createCache(classObject, methodBinder, binders);
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
	public <AnnotationType extends Annotation> void createCache(Class<?> annotatedType, final FieldBinder<AnnotationType> fieldBinder, List<Binder> binders) {
		for (final Field field : annotatedType.getDeclaredFields()) {
			final AnnotationType annotation = field.getAnnotation(fieldBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			binders.add(new Binder() {
				@Override
				public void bind(Object object, Object... modules) {
					fieldBinder.bind(object, annotation, field, modules);
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
	public <AnnotationType extends Annotation> void createCache(Class<?> annotatedType, final MethodBinder<AnnotationType> methodBinder, List<Binder> binders) {
		for (final Method method : annotatedType.getDeclaredMethods()) {
			final AnnotationType annotation = method.getAnnotation(methodBinder.getAnnotationClass());

			if (annotation == null) {
				continue;
			}

			binders.add(new Binder() {
				@Override
				public void bind(Object object, Object... modules) {
					methodBinder.bind(object, annotation, method, modules);
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
	public <AnnotationType extends Annotation> void createCache(final Class<?> annotatedType, final TypeBinder<AnnotationType> typeBinder, List<Binder> binders) {
		final @Nullable AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

		if (annotation != null) {
			binders.add(new Binder() {
				@Override
				public void bind(Object object, Object... modules) {
					typeBinder.bind(object, annotation, annotatedType, modules);
				}
			});
		}
	}
}
