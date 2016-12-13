package spork.injection;

import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;
import spork.injection.modules.StringModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InjectLazyTests {

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
		assertEquals(first, second);
	}
}
