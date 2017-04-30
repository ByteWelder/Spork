package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectLazyTests {

	/**
	 * A module that increases a counter every time a method is called.
	 */
	private static class Module {
		private int counter = 0;

		@Provides
		public Integer provideInteger() {
			return ++counter;
		}
	}

	private static class Parent {
		@Inject
		Lazy<Integer> provider;
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * the same instance is returned every time.
	 */
	@Test
	public void multipleGet() {
		Parent parent = new Parent();

		ObjectGraphs.builder()
				.module(new Module())
				.build()
				.inject(parent);

		Integer first = parent.provider.get();
		Integer second = parent.provider.get();

		assertThat(first, is(1));
		assertThat(second, is(1));
	}
}