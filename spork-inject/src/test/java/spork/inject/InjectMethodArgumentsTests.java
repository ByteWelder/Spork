package spork.inject;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;

import static org.junit.Assert.assertEquals;

public class InjectMethodArgumentsTests {

	public static class Module {

		@Named("one")
		@Provides
		public Integer one() {
			return 1;
		}

		@Named("two")
		@Provides
		public String two() {
			return "2";
		}

		@Provides
		public String combination(@Named("one") Integer first, @Named("two") String second) {
			return Integer.toString(first) + second;
		}
	}

	private static class Parent {
		@Inject
		String string = "";
	}

	@Test
	public void instanceValueTest() {
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new Module())
				.build();

		Spork.bind(parent, graph);

		assertEquals("12", parent.string);
	}

}
