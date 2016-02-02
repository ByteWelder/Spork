package io.github.sporklibrary.test.fieldbinding;

import io.github.sporklibrary.Spork;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FieldAndMethodBindingTests
{
	private TestFieldAndMethodBinder mTestBinder;

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
		mTestBinder = new TestFieldAndMethodBinder();
		Spork.getBinderManager().register(mTestBinder, mTestBinder, null);
	}

	@Test
	public void methodBinding()
	{
		Assert.assertEquals(mTestBinder.getFieldBindCount(), 0);
		Assert.assertEquals(mTestBinder.getMethodBindCount(), 0);

		BinderParent object = new BinderParent();

		Spork.bind(object);

		Assert.assertEquals(mTestBinder.getFieldBindCount(), 1);
		Assert.assertEquals(mTestBinder.getMethodBindCount(), 2);
	}
}
