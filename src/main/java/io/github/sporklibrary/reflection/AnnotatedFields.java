package io.github.sporklibrary.reflection;

import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotInstantiatableException;

import java.lang.reflect.Field;

public final class AnnotatedFields
{
	private AnnotatedFields()
	{
		throw new NotInstantiatableException(getClass());
	}

	/**
	 * Set a value for an AnnotatedField
	 * @param annotatedField the AnnotatedField
	 * @param object the parent object
	 * @param value the field value to bind
	 */
	public static void set(AnnotatedField<?> annotatedField, Object object, Object value)
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
