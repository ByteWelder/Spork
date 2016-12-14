package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.inject.modules.IntegerModule;
import spork.inject.modules.StringModule;

import static org.junit.Assert.assertEquals;

public class InjectTests {

	// todo: add test with inheritance

	private static class Parent {
		@Inject String string;
		@Inject CharSequence charSequence;
		@Inject Integer integer;
	}

	@Test
	public void regularTest() {
		// normal case with a class-bound and interface-bound field
		Parent parent = new Parent();
		Spork.bind(parent, new StringModule(), new IntegerModule());
		assertEquals("test", parent.string);
		assertEquals("test", parent.charSequence);
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
