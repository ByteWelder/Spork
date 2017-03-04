package spork.inject.internal;

import org.junit.Test;

import javax.inject.Inject;

import spork.inject.Provides;

import static org.junit.Assert.assertEquals;

public class ObjectGraphParentTests {

	public static final class ParentModule {
		@Provides
		public String stringValue() {
			return "test";
		}

		@Provides
		public int overridableInt() {
			return 1;
		}
	}

	public static final class ChildModule {
		@Provides
		public int overridingInt() {
			return 2;
		}
	}

	private static class Injectable {
		@Inject
		public String publicValue;

		@Inject
		public int overriddenInt;
	}

	@Test
	public void testObjectGraphParent() {
		ObjectGraph parentGraph = new ObjectGraphBuilder()
				.module(new ParentModule())
				.build();

		ObjectGraph childGraph = new ObjectGraphBuilder(parentGraph)
				.module(new ChildModule())
				.build();

		Injectable injectable = new Injectable();
		childGraph.inject(injectable);

		assertEquals("parent solely provides injection", "test", injectable.publicValue);
		assertEquals("child provider should override parent", 2, injectable.overriddenInt);
	}
}
