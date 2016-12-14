package spork.inject.internal;

import org.junit.Test;

import javax.annotation.Nullable;

import spork.internal.Callable;

import static org.junit.Assert.assertEquals;

public class CallableProviderTests {

	@Test
	public void constructor() {
		CallableProvider<String> provider = new CallableProvider<>(new Callable<String>() {
			@Nullable
			@Override
			public String call() {
				return "test";
			}
		});

		assertEquals("test", provider.get());
	}

	@Test(expected = NullPointerException.class)
	public void faultyConstructor() {
		CallableProvider<String> provider = new CallableProvider<>(null);
		provider.get();
	}

	@Test
	public void caching() {
		CallableProvider<Integer> provider = new CallableProvider<>(new Callable<Integer>() {
			private int counter = 0;
			@Nullable
			@Override
			public Integer call() {
				return ++counter;
			}
		});

		assertEquals((Integer) 1, provider.get());
		assertEquals((Integer) 1, provider.get());
	}
}
