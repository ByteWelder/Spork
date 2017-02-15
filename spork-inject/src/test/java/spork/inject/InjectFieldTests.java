package spork.inject;

import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.IntegerModule;

import static org.junit.Assert.assertEquals;

/**
 * Tests related to injecting fields.
 */
public class InjectFieldTests {

	private static class Parent {
		@Inject
		public static Integer staticValue = 0;

		@Inject
		public Integer publicValue = 0;

		@Inject
		private Integer protectedValue = 0;

		@Inject
		private Integer privateValue = 0;

		public Integer getProtectedValue() {
			return protectedValue;
		}

		public Integer getPrivateValue() {
			return privateValue;
		}
	}

	/**
	 * Test that when Provider.get() is called multiple times,
	 * the same instance is returned every time.
	 */
	@Test
	public void injectFieldTests() {
		// given
		Parent parent = new Parent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new IntegerModule())
				.build();

		// when
		Spork.bind(parent, graph);

		// then
		assertEquals(Integer.valueOf(1), Parent.staticValue);
		assertEquals(Integer.valueOf(1), parent.publicValue);
		assertEquals(Integer.valueOf(1), parent.getProtectedValue());
		assertEquals(Integer.valueOf(1), parent.getPrivateValue());
	}
}
