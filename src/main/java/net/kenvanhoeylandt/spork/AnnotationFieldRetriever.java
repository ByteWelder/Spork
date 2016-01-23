package net.kenvanhoeylandt.spork;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Retrieves AnnotatedField objects for given classes.
 * @param <AnnotationType> the AnnotatedField's annotation type
 */
public class AnnotationFieldRetriever<AnnotationType extends Annotation>
{
	private final Class<AnnotationType> mAnnotationClass;

	public AnnotationFieldRetriever(Class<AnnotationType> annotationClass)
	{
		mAnnotationClass = annotationClass;
	}

	private Map<Class<?>, Set<AnnotatedField>> mClassFieldSetMap = new HashMap<>();

	public Set<AnnotatedField> getAnnotatedFields(Class<?> classObject)
	{
		Set<AnnotatedField> field_set = mClassFieldSetMap.get(classObject);

		if (field_set != null)
		{
			return field_set;
		}

		field_set = new HashSet<>();

		for (Field field : classObject.getDeclaredFields())
		{
			AnnotationType inject = field.getAnnotation(mAnnotationClass);

			if (inject != null)
			{
				field_set.add(new AnnotatedField<>(inject, field));
			}
		}

		mClassFieldSetMap.put(classObject, field_set);

		return field_set;
	}
}
