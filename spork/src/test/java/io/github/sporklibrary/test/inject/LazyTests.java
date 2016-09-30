package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.interfaces.Lazy;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.test.inject.domain.StringModule;

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
