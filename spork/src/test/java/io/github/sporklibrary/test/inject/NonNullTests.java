package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.NonNull;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.inject.domain.StringModule;
import io.github.sporklibrary.test.inject.domain.StringNullModule;

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
