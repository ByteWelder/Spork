package io.github.sporklibrary.mockito.test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
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
	public void test()
	{
		// Check that normal behavior is working
		Parent parent = new Parent();
		IntGetter regular_int_getter = parent.getIntGetter();
		assertEquals("regular IntGetter value", 1, regular_int_getter.get());

		// Enable Mockito
		SporkMockito.register();

		Parent mocked_parent = new Parent();
		IntGetter mocked_int_getter = mocked_parent.getIntGetter();

		assertEquals("mocked IntGetter default value", 0, mocked_int_getter.get());
		when(mocked_int_getter.get()).thenReturn(2);
		assertEquals("mocked IntGetter overridden value", 2, mocked_int_getter.get());
	}
}
