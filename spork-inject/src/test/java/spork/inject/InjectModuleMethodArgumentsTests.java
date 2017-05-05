package spork.inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.SporkRuntimeException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectModuleMethodArgumentsTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
		public StringBuilder provideStringBuilder(Integer first) {
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

		ObjectGraphs.builder()
				.module(new Module())
				.build()
				.inject(parent);

		assertThat(parent.stringBuilder.toString(), is("12"));
	}

	@Test
	public void missingArgumentForProvidesMethod() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("Failed to resolve provider for java.lang.StringBuilder:NONNULL");

		Parent parent = new Parent();

		ObjectGraph graph = ObjectGraphs.builder()
				.module(new MissingDependencyModule())
				.build();

		Spork.bind(parent, graph);
	}
}
