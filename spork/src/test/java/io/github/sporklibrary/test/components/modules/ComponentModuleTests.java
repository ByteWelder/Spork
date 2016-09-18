package io.github.sporklibrary.test.components.modules;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.Provides;
import io.github.sporklibrary.exceptions.BindException;

import static org.junit.Assert.assertNotNull;

public class ComponentModuleTests {

	public static class GoodParent {

		@BindComponent
		private TestChild testChild;

		public GoodParent() {
			Spork.bind(this, new GoodModule());
		}

		public TestChild getTestChild() {
			return testChild;
		}
	}

	public static class BadParent {

		@BindComponent
		private TestChild testChild;

		public BadParent() {
			Spork.bind(this, new BadModule());
		}
	}

	public interface TestChild {
		void test();
	}

	public static class TestChildImpl implements TestChild {
		@Override
		public void test() {
		}
	}

	public static class GoodModule {
		@Provides
		public TestChildImpl getTestChild() {
			return new TestChildImpl();
		}
	}

	public static class BadModule {
	}

	@Test
	public void goodModule() {
		GoodParent goodParent = new GoodParent();
		assertNotNull(goodParent.getTestChild());
	}

	@Test(expected = BindException.class)
	public void badModule() {
		new BadParent();
	}
}
