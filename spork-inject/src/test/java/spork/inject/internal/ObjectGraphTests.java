package spork.inject.internal;

import org.junit.Test;

import spork.Spork;
import spork.inject.Provides;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ObjectGraphTests {
	private static class Module {
		@Provides
		public Object object() {
			return new Object();
		}
	}

	@Test
	public void bindWithCustomSpork() {
		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		Spork spork = spy(new Spork());

		Object injectable = new Object();
		graph.inject(injectable, spork);
		verify(spork).bind(injectable, graph);
	}
}
