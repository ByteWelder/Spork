package spork.inject.internal;

import org.junit.Test;

import spork.Spork;
import spork.inject.Provides;
import spork.interfaces.Binder;
import spork.interfaces.BinderRegistry;

import static org.mockito.Mockito.mock;
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
		Binder binder = mock(Binder.class);
		BinderRegistry binderRegistry = mock(BinderRegistry.class);
		Spork spork = new Spork(binderRegistry, binder);

		Object injectable = new Object();
		graph.inject(injectable, spork);
		verify(binder).bind(injectable, graph);
	}
}
