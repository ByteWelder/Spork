package io.github.sporklibrary.android.support;

import org.junit.Test;

import io.github.sporklibrary.android.support.resolvers.SupportFragmentResolver;

import static org.junit.Assert.assertNull;

public class SupportFragmentResolverTest {

	@Test
	public void testFaulty() {
		SupportFragmentResolver resolver = new SupportFragmentResolver();
		assertNull(resolver.resolveFragment("Nothing", 1));
		assertNull(resolver.resolveFragment("Nothing", "anything"));
	}
}
