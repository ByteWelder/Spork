package io.github.sporklibrary.reflection;

import io.github.sporklibrary.exceptions.BindException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class AnnotatedFields
{
	private AnnotatedFields()
	{
	}

	public static <AnnotationType extends Annotation> Set<AnnotatedField<AnnotationType>> get(Class<AnnotationType> annotationClass, Class<?> annotatedClass)
	{
		HashSet<AnnotatedField<AnnotationType>> annotated_field_set = new HashSet<>();

		for (Field field : annotatedClass.getDeclaredFields())
		{
			AnnotationType annotation = field.getAnnotation(annotationClass);

			if (annotation != null)
			{
				annotated_field_set.add(new AnnotatedField<>(annotation, field));
			}
		}

		return !annotated_field_set.isEmpty() ? annotated_field_set : Collections.<AnnotatedField<AnnotationType>>emptySet();
	}

	/**
	 * Set a value for an AnnotatedField
	 * @param annotatedField the AnnotatedField
	 * @param object the parent object
	 * @param value the field value to bind
	 */
	public static void setValue(AnnotatedField<?> annotatedField, Object object, Object value)
	{
		Field field = annotatedField.getField();

		boolean accessible = field.isAccessible();

		try
		{
			if (accessible)
			{
				field.set(object, value);
			}
			else
			{
				field.setAccessible(true);
				field.set(object, value);
				field.setAccessible(false);
			}
		}
		catch (IllegalAccessException e)
		{
			throw new BindException(annotatedField.getAnnotation().getClass(), object.getClass(), field, "field not accessible", e);
		}
		finally
		{
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && field.isAccessible())
			{
				field.setAccessible(false);
			}
		}
	}
}
