package net.kenvanhoeylandt.spork;

import net.kenvanhoeylandt.spork.component.DefaultScopedComponent;
import net.kenvanhoeylandt.spork.component.FaultyComponent;
import net.kenvanhoeylandt.spork.component.SingletonScopedComponent;
import org.junit.Assert;
import net.kenvanhoeylandt.spork.annotations.Inject;
import org.junit.Test;

public class InjectionTest
{
	public static class Parent
	{
		@Inject
		private DefaultScopedComponent mDefaultScopedComponent;

		@Inject
		private SingletonScopedComponent mSingletonScopedComponent;

		public Parent()
		{
			Spork.inject(this);
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
		@Inject
		private FaultyComponent mFaultyComponent;

		public FaultyParent()
		{
			Spork.inject(this);
		}

		public FaultyComponent getFaultyComponent()
		{
			return mFaultyComponent;
		}
	}

	@Test
	public void injection()
	{
		Parent first_parent = new Parent();
		Parent second_parent = new Parent();

		Assert.assertNotNull("default scoped component injection", first_parent.geDefaultScopedComponent());
		Assert.assertNotNull("singleton scoped component injection", first_parent.getSingletonScopedComponent());

		Assert.assertTrue("default scope instance uniqueness", first_parent.geDefaultScopedComponent() != second_parent.geDefaultScopedComponent());
		Assert.assertTrue("instance scope instance uniqueness", first_parent.getSingletonScopedComponent() == second_parent.getSingletonScopedComponent());
	}

	@Test(expected = RuntimeException.class)
	public void faultyInjection()
	{
		new FaultyParent();
	}
}
