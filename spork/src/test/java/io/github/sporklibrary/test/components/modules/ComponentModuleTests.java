package io.github.sporklibrary.test.components.modules;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.Provides;

import static org.junit.Assert.assertNotNull;

public class ComponentModuleTests {

	public static class TestParent {

		@BindComponent
		private TestChild tesetChild;

		public TestParent() {
			Spork.bind(this, new Module());
		}

		public TestChild getTestChild() {
			return tesetChild;
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

	public static class Module {
		@Provides
		public TestChild getTestChild() {
			return new TestChildImpl();
		}
	}

	@Test
	public void test() {
		TestParent testParent = new TestParent();
		assertNotNull(testParent.getTestChild());
	}
}
