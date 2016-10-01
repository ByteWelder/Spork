package spork.android.support.test;

import org.junit.Test;

import spork.android.support.internal.SupportFragmentResolver;

import static org.junit.Assert.assertNull;

public class SupportFragmentResolverTest {

	@Test
	public void testFaulty() {
		SupportFragmentResolver resolver = new SupportFragmentResolver();
		assertNull(resolver.resolveFragment("Nothing", 1));
		assertNull(resolver.resolveFragment("Nothing", "anything"));
	}
}
