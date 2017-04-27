package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectNamedTests {
	private static class Module {

		@Provides @Named("one")
		public int one() {
			return 1;
		}

		@Provides @Named("two")
		public int two() {
			return 2;
		}

		@Provides
		public int unnamed() {
			return 3;
		}
	}

	private static class Parent {
		@Inject @Named("one")
		int one;

		@Inject @Named("two")
		int two;

		@Inject
		int unnamed;
	}

	@Test
	public void namedTest() {
		Parent parent = new Parent();

		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		assertThat(parent.one, is(1));
		assertThat(parent.two, is(2));
		assertThat(parent.unnamed, is(3));
	}
}