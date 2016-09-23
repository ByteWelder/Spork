package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.Provides;
import io.github.sporklibrary.annotations.Singleton;

import static org.junit.Assert.assertEquals;

public class SingletonTests {
	private static class Parent {
		@Inject
		int counter = -1;
	}

	public class Module {
		private int counter = 1;

		@Provides
		@Singleton
		public int counter() {
			return counter++;
		}
	}

	@Test
	public void test() {
		Parent parent = new Parent();
		Module module = new Module();

		// ensure wrong defaults
		parent.counter = -1;

		Spork.bind(parent, module);
		assertEquals(1, parent.counter);
		Spork.bind(parent, module);
		assertEquals(1, parent.counter);
	}
}
