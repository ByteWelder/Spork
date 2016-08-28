package io.github.sporklibrary.mockito.test;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.mockito.MockitoFilter;
import io.github.sporklibrary.mockito.SporkMockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MockitoTests {

	@Test
	public void testDefaultMockingBehavior() {
		// Enable Mockito for all classes
		SporkMockito.initialize();

		runMockingTests();
	}

	@Test
	public void testSpecificMockingBehavior() {
		// Enable Mockito for 1 class
		SporkMockito.initialize(Component.class);

		runMockingTests();
	}

	@Test
	public void testDefaultBehavior() {
		// Check that non-mocking behavior is working

		// Enable Mockito but don't allow any types
		SporkMockito.initialize(new MockitoFilter() {
			@Override
			public boolean shouldMockWithMockito(Class<?> classObject) {
				return false;
			}
		});

		// The created instance should not be mocked, since the filter disallows it
		Parent mockedParent = new Parent();
		Component mockedComponent = mockedParent.getComponent();
		assertEquals("Component default value", 1, mockedComponent.getValue());
	}

	private void runMockingTests() {
		Parent parent = new Parent();
		Component mockedParent = parent.getComponent();

		assertEquals("mocked Component default value", 0, mockedParent.getValue());
		when(mockedParent.getValue()).thenReturn(2);
		assertEquals("mocked Component overridden value", 2, mockedParent.getValue());
	}

	public static class Component {
		public int getValue() {
			return 1;
		}
	}

	public static class Parent {
		@BindComponent
		private Component component;

		public Parent() {
			Spork.bind(this);
		}

		public Component getComponent() {
			return component;
		}
	}
}
