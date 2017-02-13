package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;
import spork.inject.modules.IncreasingIntegerModule;
import spork.inject.modules.StringModule;

import static org.junit.Assert.assertEquals;

public class InjectProviderTests {

	private static class ProviderParent {
		@Inject
		Provider<Integer> provider;
	}

	@Test
	public void multipleGet() {
		// given
		ProviderParent parent = new ProviderParent();

		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new IncreasingIntegerModule())
				.build();

		Spork.bind(parent, graph);

		// when
		Integer first = parent.provider.get();
		Integer second = parent.provider.get();

		// then
		assertEquals((Integer) 1, first);
		assertEquals((Integer) 1, second);
	}
}
