package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectSingletonTests {

	private static class Module {
		private Integer counter = 1;

		@Provides
		@Singleton
		public Integer counter() {
			return counter++;
		}
	}

	private static class Parent {
		@Inject
		Integer counter = -1;
	}

	@Test
	public void singleInstanceTest() {
		Parent parent = new Parent();

		ObjectGraph graph = ObjectGraphs.builder()
				.module(new Module())
				.build();

		// ensure wrong defaults
		parent.counter = -1;

		// inject twice
		graph.inject(parent);
		graph.inject(parent);

		// verify same value
		assertThat(parent.counter, is(1));
	}
}
