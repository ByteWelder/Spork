package spork.injection.internal;

import org.junit.Test;

import javax.annotation.Nullable;

import spork.internal.Callable;

import static org.junit.Assert.assertEquals;

public class LazyImplTests {

	@Test
	public void testConstructor() {
		LazyImpl<String> lazy = new LazyImpl<>(new Callable<String>() {
			@Nullable
			@Override
			public String call() {
				return "test";
			}
		});

		assertEquals("test", lazy.get());
	}

	@Test(expected = NullPointerException.class)
	public void testFaultyConstructor() {
		LazyImpl<String> lazy = new LazyImpl<>(null);
		lazy.get();
	}
}
