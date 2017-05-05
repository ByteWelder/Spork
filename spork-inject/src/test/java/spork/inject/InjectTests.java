package spork.inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.SporkRuntimeException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private ObjectGraph graph;

	public static class StringModule {
		@Provides
		public String stringValue() {
			return "test";
		}
	}

	public static class IntegerModule {
		@Provides
		public Integer integerValue() {
			return 1;
		}
	}

	private static class InjectTarget {
		@Inject String string;
		@Inject Integer integer;
	}

	private static class InjectInheritanceTarget extends InjectTarget {
		@Inject String extensionString;
	}

	@Before
	public void setup() {
		graph = ObjectGraphs.builder()
				.module(new StringModule())
				.module(new IntegerModule())
				.build();
	}

	@Test
	public void injectByObjectGraph() {
		// normal case with a class-bound and interface-bound field
		InjectTarget injectTarget = new InjectTarget();

		graph.inject(injectTarget);

		assertThat(injectTarget.string, is("test"));
		assertThat(injectTarget.integer, is(1));
	}

	@Test
	public void injectInheritance() {
		InjectInheritanceTarget injectTarget = new InjectInheritanceTarget();

		graph.inject(injectTarget);

		assertThat(injectTarget.string, is("test"));
		assertThat(injectTarget.integer, is(1));
		assertThat(injectTarget.extensionString, is("test"));
	}

	@Test
	public void injectWithForeignAndGraphObjectArgument() {
		// normal case with a class-bound and interface-bound field
		InjectTarget injectTarget = new InjectTarget();

		Spork.bind(injectTarget, graph, "shouldBeIgnoredBySporkInject");

		assertThat(injectTarget.string, is("test"));
		assertThat(injectTarget.integer, is(1));
	}

	@Test
	public void injectWithForeignArgument() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("No ObjectGraph specified in instance arguments of bind()");

		InjectTarget injectTarget = new InjectTarget();
		Spork.bind(injectTarget, new StringModule());
	}

	@Test
	public void injectWithoutGraphArgument() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("No ObjectGraph specified in instance arguments of bind()");

		InjectTarget injectTarget = new InjectTarget();
		Spork.bind(injectTarget);
	}
}
