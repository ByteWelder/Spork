package io.github.sporklibrary.mockito.test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.mockito.MockitoFilter;
import io.github.sporklibrary.mockito.SporkMockito;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MockitoTests
{
	public interface IntGetter
	{
		int get();
	}

	public static class IntGetterImplementation implements IntGetter
	{
		@Override
		public int get()
		{
			return 1;
		}
	}

	public static class Parent
	{
		@BindComponent(IntGetterImplementation.class)
		private IntGetter mIntGetter;

		public Parent()
		{
			Spork.bind(this);
		}

		public IntGetter getIntGetter()
		{
			return mIntGetter;
		}
	}

	@Test
	public void testDefaultMockingBehavior()
	{
		// Enable Mockito for all classes
		SporkMockito.initialize();

		runMockingTests();
	}

	@Test
	public void testSpecificMockingBehavior()
	{
		// Enable Mockito for 1 class
		SporkMockito.initialize(IntGetterImplementation.class);

		runMockingTests();
	}

	@Test
	public void testDefaultBehavior()
	{
		// Check that non-mocking behavior is working

		// Enable Mockito but don't allow any types
		SporkMockito.initialize(new MockitoFilter() {
			@Override
			public boolean shouldMockWithMockito(Class<?> classObject)
			{
				return false;
			}
		});

		// The created instance should not be mocked, since the filter disallows it
		Parent mocked_parent = new Parent();
		IntGetter mocked_int_getter = mocked_parent.getIntGetter();
		assertEquals("IntGetter default value", 1, mocked_int_getter.get());
	}

	private void runMockingTests()
	{
		Parent mocked_parent = new Parent();
		IntGetter mocked_int_getter = mocked_parent.getIntGetter();

		assertEquals("mocked IntGetter default value", 0, mocked_int_getter.get());
		when(mocked_int_getter.get()).thenReturn(2);
		assertEquals("mocked IntGetter overridden value", 2, mocked_int_getter.get());
	}
}
