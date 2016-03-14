package io.github.sporklibrary.test.components.inheritance;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ComponentInheritanceTest
{
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

	public static class Component extends ComponentBase
	{
		@BindComponent
		private SubComponent mSubComponentB;

		public SubComponent getSubComponentB()
		{
			return mSubComponentB;
		}
	}

	public static class ComponentBase
	{
		@BindComponent
		private SubComponent mSubComponentA;

		public ComponentBase()
		{
			Spork.bind(this);
		}

		public SubComponent getSubComponentA()
		{
			return mSubComponentA;
		}
	}

	public static class SubComponent
	{
		public static int sInstanceCount = 0;

		public SubComponent()
		{
			sInstanceCount++;
		}
	}

	@Test
	public void test()
	{
		Parent parent = new Parent();

		assertNotNull(parent.getComponent());
		assertNotNull(parent.getComponent().getSubComponentA());
		assertNotNull(parent.getComponent().getSubComponentB());

		// assure there were only 2 injections
		assertEquals(2, SubComponent.sInstanceCount);
	}
}
