package io.github.sporklibrary;

import io.github.sporklibrary.component.DefaultScopedComponent;
import io.github.sporklibrary.component.FaultyComponent;
import io.github.sporklibrary.component.SingletonScopedComponent;
import io.github.sporklibrary.exceptions.BindException;
import org.junit.Assert;
import io.github.sporklibrary.annotations.BindComponent;
import org.junit.Test;

public class BinderTest
{
	public static class Parent
	{
		@BindComponent
		private DefaultScopedComponent mDefaultScopedComponent;

		@BindComponent
		private SingletonScopedComponent mSingletonScopedComponent;

		public Parent()
		{
			Spork.bind(this);
		}

		public DefaultScopedComponent geDefaultScopedComponent()
		{
			return mDefaultScopedComponent;
		}

		public SingletonScopedComponent getSingletonScopedComponent()
		{
			return mSingletonScopedComponent;
		}
	}

	public static class FaultyParent
	{
		@BindComponent
		private FaultyComponent mFaultyComponent;

		public FaultyParent()
		{
			Spork.bind(this);
		}

		public FaultyComponent getFaultyComponent()
		{
			return mFaultyComponent;
		}
	}

	@Test
	public void binding()
	{
		Parent first_parent = new Parent();
		Parent second_parent = new Parent();

		Assert.assertNotNull("default scoped component binding", first_parent.geDefaultScopedComponent());
		Assert.assertNotNull("singleton scoped component binding", first_parent.getSingletonScopedComponent());

		Assert.assertTrue("default scope instance uniqueness", first_parent.geDefaultScopedComponent() != second_parent.geDefaultScopedComponent());
		Assert.assertTrue("instance scope instance uniqueness", first_parent.getSingletonScopedComponent() == second_parent.getSingletonScopedComponent());
	}

	@Test(expected = BindException.class)
	public void faultyBinding()
	{
		new FaultyParent();
	}
}
