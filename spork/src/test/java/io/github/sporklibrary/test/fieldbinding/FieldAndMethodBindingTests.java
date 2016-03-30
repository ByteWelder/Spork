package io.github.sporklibrary.test.fieldbinding;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldAndMethodBindingTests
{
	private BindFieldOrMethodBinder testBinder;

	public static class BinderParent
	{
		@BindFieldOrMethod
		private Object mField;

		@BindFieldOrMethod
		public void test()
		{
		}

		@BindFieldOrMethod
		public static void testStatic()
		{
		}
	}

	@Before
	public void registerTestBinders()
	{
		testBinder = new BindFieldOrMethodBinder();

		Spork.getBinderManager().register((FieldBinder) testBinder);
		Spork.getBinderManager().register((MethodBinder) testBinder);
	}

	@Test
	public void methodBinding()
	{
		Assert.assertEquals(testBinder.getFieldBindCount(), 0);
		Assert.assertEquals(testBinder.getMethodBindCount(), 0);

		BinderParent object = new BinderParent();

		Spork.bind(object);

		Assert.assertEquals(testBinder.getFieldBindCount(), 1);
		Assert.assertEquals(testBinder.getMethodBindCount(), 2);
	}
}
