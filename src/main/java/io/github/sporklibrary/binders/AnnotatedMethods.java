package io.github.sporklibrary.binders;

import io.github.sporklibrary.exceptions.BindException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotatedMethods
{
	/**
	 * Invoke an AnnotatedMethod
	 * @param annotatedMethod the AnnotatedMethod
	 * @param object the parent object
	 * @param args the field value to bind
	 */
	public static void invoke(AnnotatedMethod<?> annotatedMethod, Object object, Object... args)
	{
		Method method = annotatedMethod.getMethod();

		boolean accessible = method.isAccessible();

		try
		{
			if (accessible)
			{
				method.invoke(object, args);
			}
			else
			{
				method.setAccessible(true);
				method.invoke(object, args);
				method.setAccessible(false);
			}
		}
		catch (IllegalAccessException e)
		{
			throw new BindException(annotatedMethod.getAnnotation().getClass(), object.getClass(), method, "method not accessible", e);
		}
		catch (InvocationTargetException e)
		{
			throw new BindException(annotatedMethod.getAnnotation().getClass(), object.getClass(), method, "method calling failed because of an invocation issue", e);
		}
		finally
		{
			// ensure the Field isn't accessible when it shouldn't be
			if (!accessible && method.isAccessible())
			{
				method.setAccessible(false);
			}
		}
	}
}
