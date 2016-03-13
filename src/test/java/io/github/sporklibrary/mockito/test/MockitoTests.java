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
	public static class Component
	{
		public int getValue()
		{
			return 1;
		}
	}

	public static class Parent
	{
		@BindComponent
		private Component mComponent;

		public Parent()
		{
			Spork.bind(this);
		}

		public Component getComponent()
		{
			return mComponent;
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
		SporkMockito.initialize(Component.class);

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
		Component mocked_component = mocked_parent.getComponent();
		assertEquals("Component default value", 1, mocked_component.getValue());
	}

	private void runMockingTests()
	{
		Parent parent = new Parent();
		Component mocked_component = parent.getComponent();

		assertEquals("mocked Component default value", 0, mocked_component.getValue());
		when(mocked_component.getValue()).thenReturn(2);
		assertEquals("mocked Component overridden value", 2, mocked_component.getValue());
	}
}
