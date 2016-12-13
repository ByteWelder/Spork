package spork.injection.internal;

import org.junit.Test;

import javax.annotation.Nullable;

import spork.internal.Callable;

import static org.junit.Assert.assertEquals;

public class LazyImplTests {

	@Test
	public void constructor() {
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
	public void faultyConstructor() {
		LazyImpl<String> lazy = new LazyImpl<>(null);
		lazy.get();
	}

	@Test
	public void caching() {
		LazyImpl<Integer> lazy = new LazyImpl<>(new Callable<Integer>() {
			private int counter = 0;
			@Nullable
			@Override
			public Integer call() {
				return ++counter;
			}
		});

		assertEquals((Integer) 1, lazy.get());
		assertEquals((Integer) 1, lazy.get());
	}
}
