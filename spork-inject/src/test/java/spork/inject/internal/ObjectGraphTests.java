package spork.inject.internal;

import org.junit.Test;

import spork.Spork;
import spork.inject.Provides;
import spork.internal.BindActionCache;
import spork.internal.Binder;
import spork.internal.Registry;

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

		Registry registry = mock(Registry.class);
		BindActionCache bindActionCache = mock(BindActionCache.class);
		Binder binder = mock(Binder.class);

		Spork spork = new Spork(registry, bindActionCache, binder);

		Object injectable = new Object();
		graph.inject(injectable, spork);
		verify(binder).bind(injectable, graph);
	}
}
