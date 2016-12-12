package spork.injection;

import org.junit.Test;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import spork.Spork;
import spork.exceptions.BindException;
import spork.injection.modules.StringModule;
import spork.injection.modules.StringNullModule;

import static org.junit.Assert.assertEquals;

public class InjectNonnullTests {
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
