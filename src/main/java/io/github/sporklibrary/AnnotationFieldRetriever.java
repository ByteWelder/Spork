package io.github.sporklibrary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
class AnnotationFieldRetriever<AnnotationType extends Annotation>
{
	private final Class<AnnotationType> mAnnotationClass;

	public AnnotationFieldRetriever(Class<AnnotationType> annotationClass)
	{
		mAnnotationClass = annotationClass;
	}

	private final Map<Class<?>, Set<AnnotatedField<AnnotationType>>> mClassFieldSetMap = new HashMap<>();

	public Set<AnnotatedField<AnnotationType>> getAnnotatedFields(Class<?> classObject)
	{
		Set<AnnotatedField<AnnotationType>> annotated_field_set = mClassFieldSetMap.get(classObject);

		if (annotated_field_set != null)
		{
			return annotated_field_set;
		}

		annotated_field_set = new HashSet<>();

		for (Field field : classObject.getDeclaredFields())
		{
			AnnotationType annotation = field.getAnnotation(mAnnotationClass);

			if (annotation != null)
			{
				annotated_field_set.add(new AnnotatedField<>(annotation, field));
			}
		}

		mClassFieldSetMap.put(classObject, annotated_field_set);

		return annotated_field_set;
	}
}
