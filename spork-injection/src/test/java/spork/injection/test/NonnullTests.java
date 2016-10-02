package spork.injection.test;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.injection.test.modules.StringModule;
import spork.injection.test.modules.StringNullModule;

import static org.junit.Assert.assertEquals;

public class NonnullTests {
	private static class Parent {
		@Inject
		@Nonnull
		String string = "";
	}

	@Test
	public void instanceValueTest() {
		Parent parent = new Parent();
		Spork.bind(parent, new StringModule());
		assertEquals("test", parent.string);
	}

	@Test(expected = BindException.class)
	public void nullValueTest() {
		Parent parent = new Parent();
		Spork.bind(parent, new StringNullModule());
	}
}
