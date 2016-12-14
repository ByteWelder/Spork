package spork.inject;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.inject.modules.StringModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InjectProviderTests {

	private static class ProviderParent {
		@Inject
		Provider<String> provider;
	}

	@Test
	public void test() {
		ProviderParent parent = new ProviderParent();
		Spork.bind(parent, new StringModule());

		assertNotNull(parent.provider);

		String first = parent.provider.get();
		assertEquals("test", first);

		String second = parent.provider.get();
		assertNotNull(second);
		assertEquals(first, second);
	}
}
