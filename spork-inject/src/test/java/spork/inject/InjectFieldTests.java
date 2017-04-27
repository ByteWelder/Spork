package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
		ObjectGraphs.builder()
				.module(new Module())
				.build()
				.inject(parent);

		// then
		assertThat(Parent.staticValue, is(1));
		assertThat(parent.publicValue, is(1));
		assertThat(parent.getProtectedValue(), is(1));
		assertThat(parent.getPrivateValue(), is(1));
	}
}
