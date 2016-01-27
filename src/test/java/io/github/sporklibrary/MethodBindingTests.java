package io.github.sporklibrary;

import io.github.sporklibrary.annotations.BindMethod;
import io.github.sporklibrary.binders.TestMethodBinder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MethodBindingTests
{
	private TestMethodBinder mTestMethodBinder;

	public static class MethodBinderParent
	{
		@BindMethod
		public void test()
		{
		}

		@BindMethod
		public static void testStatic(int a)
		{
		}
	}

	@Before
	public void registerTestBinders()
	{
		mTestMethodBinder = new TestMethodBinder();
		Spork.getBinderManager().register(mTestMethodBinder);
	}

	@Test
	public void methodBinding()
	{
		Assert.assertEquals(mTestMethodBinder.getMethodCount(), 0);

		MethodBinderParent object = new MethodBinderParent();

		Spork.bind(object);

		Assert.assertEquals(mTestMethodBinder.getMethodCount(), 2);
	}
}
