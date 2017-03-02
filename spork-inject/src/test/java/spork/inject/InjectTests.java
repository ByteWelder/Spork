package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.BindException;
import spork.Spork;
import spork.inject.internal.ObjectGraphBuilder;

import static org.junit.Assert.assertEquals;

public class InjectTests {

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

		assertEquals("test", parent.string);
		assertEquals(Integer.valueOf(1), parent.integer);
	}

	@Test(expected = BindException.class)
	public void oneMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent, new StringModule());
	}

	@Test(expected = BindException.class)
	public void allMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent);
	}
}
