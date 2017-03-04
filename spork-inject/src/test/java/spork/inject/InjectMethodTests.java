package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.BindException;
import spork.Spork;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InjectMethodTests {
	public static class Module {
		@Provides
		public Integer integerValue() {
			return 1;
		}
	}

	private static class Parent {
		public static boolean staticMethodCalled = false;
		public boolean noArgumentsCalled = false;
		public boolean argumentMethodCalled = false;
		public Integer argumentMethodArgument = null;
		public boolean returnValueMethodCalled = false;

		@Inject
		public static void staticMethod() {
			staticMethodCalled = true;
		}

		@Inject
		public void noArguments() {
			noArgumentsCalled = true;
		}

		@Inject
		public void argumentMethod(Integer value) {
			argumentMethodArgument = value;
			argumentMethodCalled = true;
		}

		@Inject
		public int returnValueMethod() {
			returnValueMethodCalled = true;
			return 1;
		}
	}

	@Test
	public void injectMethodTest() {
		// given
		Parent parent = new Parent();

		// when
		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		// then
		assertTrue(Parent.staticMethodCalled);
		assertTrue(parent.argumentMethodCalled);
		assertEquals(Integer.valueOf(1), parent.argumentMethodArgument);
		assertTrue(parent.returnValueMethodCalled);
		assertTrue(parent.noArgumentsCalled);
	}

	@Test(expected = BindException.class)
	public void injectWithoutGraph() {
		Parent parent = new Parent();
		Spork.bind(parent);
	}
}
