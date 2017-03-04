package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Tests related to injecting fields.
 */
public class InjectFieldTests {

	private static class Module {
		@Provides
		public Integer integerValue() {
			return 1;
		}
	}

	private static class Parent {
		@Inject
		static Integer staticValue = 0;

		@Inject
		private Integer publicValue = 0;

		@Inject
		private Integer protectedValue = 0;

		@Inject
		private Integer privateValue = 0;

		Integer getProtectedValue() {
			return protectedValue;
		}

		private Integer getPrivateValue() {
			return privateValue;
		}
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * the same instance is returned every time.
	 */
	@Test
	public void injectFieldTests() {
		// given
		Parent parent = new Parent();

		// when
		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		// then
		assertEquals(Integer.valueOf(1), Parent.staticValue);
		assertEquals(Integer.valueOf(1), parent.publicValue);
		assertEquals(Integer.valueOf(1), parent.getProtectedValue());
		assertEquals(Integer.valueOf(1), parent.getPrivateValue());
	}
}
