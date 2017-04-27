package spork.inject.internal;

import org.junit.Test;

import javax.inject.Inject;

import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
		ObjectGraph parentGraph = ObjectGraphs.builder()
				.module(new ParentModule())
				.build();

		ObjectGraph childGraph = ObjectGraphs.builder(parentGraph)
				.module(new ChildModule())
				.build();

		Injectable injectable = new Injectable();
		childGraph.inject(injectable);

		assertThat(injectable.publicValue, is("test")); // parent solely provides injection
		assertThat(injectable.overriddenInt, is(2)); // child provider should override paren
	}
}
