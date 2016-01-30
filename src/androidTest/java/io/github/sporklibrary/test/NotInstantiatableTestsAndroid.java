package io.github.sporklibrary.test;

import io.github.sporklibrary.exceptions.NotInstantiatableException;
import io.github.sporklibrary.utils.ContextResolver;
import io.github.sporklibrary.utils.FragmentResolver;
import io.github.sporklibrary.utils.ViewResolver;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NotInstantiatableTestsAndroid
{
	@Test(expected = NotInstantiatableException.class)
	public void contextResolverClass()
	{
		createInstance(ContextResolver.class);
	}

	@Test(expected = NotInstantiatableException.class)
	public void fragmentResolverClass()
	{
		createInstance(FragmentResolver.class);
	}

	@Test(expected = NotInstantiatableException.class)
	public void viewResolverClass()
	{
		createInstance(ViewResolver.class);
	}

	public static <T> void createInstance(Class<T> type)
	{
		try
		{
			Constructor<T> constructor = type.getDeclaredConstructor();

			boolean accessible = constructor.isAccessible();

			constructor.setAccessible(true);
			constructor.newInstance();
			constructor.setAccessible(accessible);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
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
