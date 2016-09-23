package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.inject.domain.IntegerModule;
import io.github.sporklibrary.test.inject.domain.StringModule;

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
