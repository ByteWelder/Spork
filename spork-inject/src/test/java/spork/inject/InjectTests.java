package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.BindFailed;
import spork.Spork;
import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectTests {

	private static class StringModule {
		@Provides
		public String stringValue() {
			return "test";
		}
	}

	private static class IntegerModule {
		@Provides
		public Integer integerValue() {
			return 1;
		}
	}

	// todo: add test with inheritance

	private static class Parent {
		@Inject String string;
		@Inject Integer integer;
	}

	@Test
	public void regularTest() {
		// normal case with a class-bound and interface-bound field
		Parent parent = new Parent();

		new ObjectGraphBuilder()
				.module(new StringModule())
				.module(new IntegerModule())
				.build()
				.inject(parent);

		assertThat(parent.string, is("test"));
		assertThat(parent.integer, is(1));
	}

	@Test(expected = BindFailed.class)
	public void oneMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent, new StringModule());
	}

	@Test(expected = BindFailed.class)
	public void allMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent);
	}
}
