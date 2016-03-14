package io.github.sporklibrary.test.components.inheritance;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ParentInheritanceTest
{
	public static class Parent extends ParentBase
	{
		@BindComponent
		private Component mComponent;

		public Component getComponent()
		{
			return mComponent;
		}
	}

	public static class ParentBase
	{
		public ParentBase()
		{
			Spork.bind(this);
		}
	}

	public static class Component
	{
	}

	@Test
	public void test()
	{
		Parent parent = new Parent();

		assertNotNull(parent.getComponent());
	}
}
