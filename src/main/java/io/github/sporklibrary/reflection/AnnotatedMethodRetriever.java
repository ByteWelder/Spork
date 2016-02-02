package io.github.sporklibrary.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scans classes to retrieve annotated fields in them.
 * It also caches them so repeated calls will be much faster.
 *
 * @param <AnnotationType> the AnnotatedField's annotation type
 */
public class AnnotatedMethodRetriever<AnnotationType extends Annotation>
{
	private final Class<AnnotationType> mAnnotationClass;

	public AnnotatedMethodRetriever(Class<AnnotationType> annotationClass)
	{
		mAnnotationClass = annotationClass;
	}

	private final Map<Class<?>, Set<AnnotatedMethod<AnnotationType>>> mCache = new HashMap<>();

	public Set<AnnotatedMethod<AnnotationType>> getAnnotatedMethods(Class<?> classObject)
	{
		Set<AnnotatedMethod<AnnotationType>> annotated_method_set = mCache.get(classObject);

		if (annotated_method_set != null)
		{
			return annotated_method_set;
		}

		annotated_method_set = new HashSet<>();

		for (Method method : classObject.getDeclaredMethods())
		{
			AnnotationType annotation = method.getAnnotation(mAnnotationClass);

			if (annotation != null)
			{
				annotated_method_set.add(new AnnotatedMethod<>(annotation, method));
			}
		}

		mCache.put(classObject, annotated_method_set);

		return annotated_method_set;
	}
}