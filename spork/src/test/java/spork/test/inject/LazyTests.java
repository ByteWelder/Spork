package spork.test.inject;

import org.junit.Test;

import spork.interfaces.Lazy;
import spork.Spork;
import spork.annotations.Inject;
import spork.test.inject.domain.StringModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LazyTests {

	private static class SimpleParentLazy {
		@Inject Lazy<String> lazyString;
	}

	@Test
	public void test() {
		SimpleParentLazy parent = new SimpleParentLazy();
		Spork.bind(parent, new StringModule());

		assertNotNull(parent.lazyString);

		String first = parent.lazyString.get();
		assertEquals("test", first);

		String second = parent.lazyString.get();
		assertNotNull(second);
		assertTrue(first == second);
	}
}
