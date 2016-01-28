package io.github.sporklibrary.binders;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.exceptions.NotInstantiatableException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class AnnotatedMethods
{
	private AnnotatedMethods()
	{
		throw new NotInstantiatableException(getClass());
	}

	/**
	 * Invoke an AnnotatedMethod
	 * @param annotatedMethod the AnnotatedMethod
	 * @param object the parent object
	 * @param args the field value to bind
	 * @return the result of the invoked method
	 */
	public static @Nullable Object invoke(AnnotatedMethod<?> annotatedMethod, Object object, Object... args)
	{
		Method method = annotatedMethod.getMethod();

		boolean accessible = method.isAccessible();

		try
		{
			if (accessible)
			{
				return method.invoke(object, args);
			}
			else
			{
				method.setAccessible(true);
				Object result = method.invoke(object, args);
				method.setAccessible(false);
				return result;
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
