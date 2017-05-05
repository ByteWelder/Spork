package spork.inject.internal;

import org.junit.Test;

import spork.SporkInstance;
import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ObjectGraphTests {
	public static class Module {
		@Provides
		public Object object() {
			return new Object();
		}
	}

	@Test
	public void bindWithCustomSpork() {
		ObjectGraph graph = ObjectGraphs.builder()
				.module(new Module())
				.build();

		SporkInstance spork = spy(new SporkInstance());

		Object injectable = new Object();
		graph.inject(injectable, spork);
		verify(spork).bind(injectable, graph);
	}
}
