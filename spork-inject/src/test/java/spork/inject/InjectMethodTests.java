package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.BindFailed;
import spork.Spork;
import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectMethodTests {
	private static class Module {
		@Provides
		public Integer integerValue() {
			return 1;
		}
	}

	private static class Parent {
		static boolean staticMethodCalled = false;
		boolean noArgumentsCalled = false;
		boolean argumentMethodCalled = false;
		Integer argumentMethodArgument = null;
		boolean returnValueMethodCalled = false;

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
		assertThat(Parent.staticMethodCalled, is(true));
		assertThat(parent.argumentMethodCalled, is(true));
		assertThat(parent.argumentMethodArgument, is(1));
		assertThat(parent.returnValueMethodCalled, is(true));
		assertThat(parent.noArgumentsCalled, is(true));
	}

	@Test(expected = BindFailed.class)
	public void injectWithoutGraph() {
		Parent parent = new Parent();
		Spork.bind(parent);
	}
}
