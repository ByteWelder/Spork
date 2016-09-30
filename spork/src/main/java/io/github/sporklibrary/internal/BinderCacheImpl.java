package io.github.sporklibrary.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.sporklibrary.interfaces.Binder;
import io.github.sporklibrary.annotations.Nullable;
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
	 * @param cachedBinders    the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void createCache(Class<?> annotatedType, final FieldBinder<AnnotationType> fieldBinder, List<Binder> cachedBinders) {
		List<AnnotatedField<AnnotationType>> annotatedFields = AnnotatedFields.get(fieldBinder.getAnnotationClass(), annotatedType);

		for (final AnnotatedField<AnnotationType> annotatedField : annotatedFields) {
			cachedBinders.add(new Binder() {
				@Override
				public void bind(Object object, @Nullable Object... modules) {
					fieldBinder.bind(object, annotatedField.getAnnotation(), annotatedField.getField(), modules);
				}
			});
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotated Method
	 * @param methodBinder     the method binder to cache annotated methods for
	 * @param cachedBinders    the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void createCache(Class<?> annotatedType, final MethodBinder<AnnotationType> methodBinder, List<Binder> cachedBinders) {
		List<AnnotatedMethod<AnnotationType>> annotatedMethods = AnnotatedMethods.get(methodBinder.getAnnotationClass(), annotatedType);

		for (final AnnotatedMethod<AnnotationType> annotatedMethod : annotatedMethods) {
			cachedBinders.add(new Binder() {
				@Override
				public void bind(Object object, @Nullable Object... modules) {
					methodBinder.bind(object, annotatedMethod.getAnnotation(), annotatedMethod.getMethod(), modules);
				}
			});
		}
	}

	/**
	 * Update the cache with this binder
	 *
	 * @param annotatedType    the parent type that holds the annotation
	 * @param typeBinder       the type binder to cache annotated types for
	 * @param cachedBinders    the list of cached Binders to add new cached Binders to
	 * @param <AnnotationType> the annotation to search for in the annotated type
	 */
	public <AnnotationType extends Annotation> void createCache(final Class<?> annotatedType, final TypeBinder<AnnotationType> typeBinder, List<Binder> cachedBinders) {
		final @Nullable AnnotationType annotation = annotatedType.getAnnotation(typeBinder.getAnnotationClass());

		if (annotation != null) {
			cachedBinders.add(new Binder() {
				@Override
				public void bind(Object object, @Nullable Object... modules) {
					typeBinder.bind(object, annotation, annotatedType, modules);
				}
			});
		}
	}
}
