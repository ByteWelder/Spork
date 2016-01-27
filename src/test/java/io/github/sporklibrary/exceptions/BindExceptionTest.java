package io.github.sporklibrary.exceptions;

import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.components.scope.DefaultScopedComponent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BindExceptionTest
{
	public class TestClass
	{
		// Must be public to be easily accessible by test
		public Object mObject;

		public TestClass(Object object)
		{
			mObject = object;
		}
	}

	@Test(expected = BindException.class)
	public void testThrow1()
	{
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, "reason");
	}

	@Test(expected = BindException.class)
	public void testThrow2()
	{
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, "reason", new Exception());
	}

	@Test(expected = BindException.class)
	public void testThrow3()
	{
		Field field = TestClass.class.getFields()[0];
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, field, "reason");
	}

	@Test(expected = BindException.class)
	public void testThrow4()
	{
		Field field = TestClass.class.getFields()[0];
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, field, "reason", new Exception());
	}

	@Test(expected = BindException.class)
	public void testThrow5()
	{
		Method method = TestClass.class.getMethods()[0];
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, method, "reason");
	}

	@Test(expected = BindException.class)
	public void testThrow6()
	{
		Method method = TestClass.class.getMethods()[0];
		throw new BindException(BindComponent.class, DefaultScopedComponent.class, method, "reason", new Exception());
	}
}
