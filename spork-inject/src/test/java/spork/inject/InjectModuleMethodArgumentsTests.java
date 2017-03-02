package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;
import spork.BindException;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectModuleMethodArgumentsTests {

	public static class Module {

		@Provides
		public Integer one() {
			return 1;
		}

		@Provides
		public String two() {
			return "2";
		}

		@Provides
		public StringBuilder combination(Integer first, String second) {
			return new StringBuilder()
					.append(first)
					.append(second);
		}
	}

	public static class MissingDependencyModule {

		@Provides
		public StringBuilder stringBuilder(Integer first) {
			return new StringBuilder().append(first);
		}
	}

	private static class Parent {
		@Inject
		StringBuilder stringBuilder;
	}

	@Test
	public void methodTest() {
		Parent parent = new Parent();

		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		assertEquals("12", parent.stringBuilder.toString());
	}

	@Test(expected = BindException.class)
	public void noDependencyFoundForArgumentTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new MissingDependencyModule())
				.build();

		Spork.bind(parent, graph);
	}
}
