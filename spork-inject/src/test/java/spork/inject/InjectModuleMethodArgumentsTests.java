package spork.inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.inject.Inject;

import spork.BindFailed;
import spork.Spork;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectModuleMethodArgumentsTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static class Module {

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

	private static class MissingDependencyModule {

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

		new ObjectGraphBuilder()
				.module(new Module())
				.build()
				.inject(parent);

		assertThat(parent.stringBuilder.toString(), is("12"));
	}

	@Test
	public void missingArgumentForProvidesMethod() {
		expectedException.expect(BindFailed.class);
		expectedException.expectMessage("failed to call spork.inject.InjectModuleMethodArgumentsTests$MissingDependencyModule.provideStringBuilder(): invocation argument not found: java.lang.Integer:NONNULL");

		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new MissingDependencyModule())
				.build();

		Spork.bind(parent, graph);
	}
}
