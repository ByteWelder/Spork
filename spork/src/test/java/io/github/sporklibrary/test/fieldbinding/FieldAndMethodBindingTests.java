package io.github.sporklibrary.test.fieldbinding;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;

public class FieldAndMethodBindingTests {
	private BindFieldOrMethodBinder testBinder;
	private final Spork spork = new Spork();

	public static class BinderParent {

		@BindFieldOrMethod
		private Object field;

		@BindFieldOrMethod
		public void test() {
		}

		@BindFieldOrMethod
		public static void testStatic() {
		}
	}

	@Before
	public void registerTestBinders() {
		testBinder = new BindFieldOrMethodBinder();
		spork.getBinderRegistry().register((FieldBinder<BindFieldOrMethod>) testBinder);
		spork.getBinderRegistry().register((MethodBinder<BindFieldOrMethod>) testBinder);
	}

	@Test
	public void methodBinding() {
		Assert.assertEquals(0, testBinder.getFieldBindCount());
		Assert.assertEquals(0, testBinder.getMethodBindCount());

		BinderParent object = new BinderParent();

		spork.getBinder().bind(object);

		Assert.assertEquals(1, testBinder.getFieldBindCount());
		Assert.assertEquals(2, testBinder.getMethodBindCount());
	}
}
