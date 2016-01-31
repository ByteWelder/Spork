package io.github.sporklibrary;

import io.github.sporklibrary.binders.AnnotatedFields;
import io.github.sporklibrary.binders.AnnotatedMethods;
import io.github.sporklibrary.exceptions.NotInstantiatableException;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NotInstantiatableTests
{
	@Test(expected = NotInstantiatableException.class)
	public void sporkClass()
	{
		createInstance(Spork.class);
	}

	@Test(expected = NotInstantiatableException.class)
	public void annotatedMethodsClass()
	{
		createInstance(AnnotatedMethods.class);
	}

	@Test(expected = NotInstantiatableException.class)
	public void annotatedFieldsClass()
	{
		createInstance(AnnotatedFields.class);
	}

	private static <T> void createInstance(Class<T> type)
	{
		try
		{
			Constructor<T> constructor = type.getDeclaredConstructor();

			boolean accessible = constructor.isAccessible();

			constructor.setAccessible(true);
			constructor.newInstance();
			constructor.setAccessible(accessible);
		}
		catch (NoSuchMethodException | InstantiationException | IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getCause() != null
				&& e.getCause().getClass().equals(NotInstantiatableException.class))
			{
				throw (NotInstantiatableException)e.getCause();
			}
			else
			{
				throw new RuntimeException(e);
			}
		}
	}
}
