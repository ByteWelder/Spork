package spork.injection;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import spork.Spork;

import static org.junit.Assert.assertEquals;

public class InjectSingletonTests {
	public static class Parent {
		@Inject
		int counter = -1;
	}

	public static class Module {
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
