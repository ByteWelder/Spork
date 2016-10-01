package spork.test.inject;

import org.junit.Test;

import spork.Spork;
import spork.annotations.Inject;
import spork.annotations.NonNull;
import spork.exceptions.BindException;
import spork.test.inject.domain.StringModule;
import spork.test.inject.domain.StringNullModule;

import static org.junit.Assert.assertEquals;

public class NonNullTests {
	private static class Parent {
		@Inject @NonNull String string;
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
