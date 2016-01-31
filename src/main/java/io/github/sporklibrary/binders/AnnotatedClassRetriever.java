package io.github.sporklibrary.binders;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
/**
 * Scans classes to retrieve annotated fields in them.
 * It also caches them so repeated calls will be much faster.
 *
 * @param <AnnotationType> the AnnotatedField's annotation type
 */
class AnnotatedClassRetriever<AnnotationType extends Annotation>
{
	private final Class<AnnotationType> mAnnotationClass;

	private final Map<Class<?>, AnnotatedClass<AnnotationType>> mCache = new HashMap<>();

	public AnnotatedClassRetriever(Class<AnnotationType> annotationClass)
	{
		mAnnotationClass = annotationClass;
	}

	public AnnotatedClass<AnnotationType> getAnnotatedClass(Class<?> classObject)
	{
		AnnotatedClass<AnnotationType> annotated_class = mCache.get(classObject);

		if (annotated_class != null)
		{
			return annotated_class;
		}

		AnnotationType annotation = classObject.getAnnotation(mAnnotationClass);

		if (annotation != null)
		{
			annotated_class = new AnnotatedClass<>(annotation, mAnnotationClass);
		}

		mCache.put(classObject, annotated_class);

		return annotated_class;
	}
}
