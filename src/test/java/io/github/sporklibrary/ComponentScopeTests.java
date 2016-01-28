package io.github.sporklibrary;

import io.github.sporklibrary.components.scope.DefaultScopedComponent;
import io.github.sporklibrary.components.scope.FaultyComponent;
import io.github.sporklibrary.components.scope.SingletonScopedComponent;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.annotations.BindComponent;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentScopeTests
{
	public static class Parent
	{
		// must be public for easy test access
		@BindComponent
		public DefaultScopedComponent mDefaultScopedComponent;

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
	public void defaultScopeClass() throws NoSuchFieldException
	{
		Parent parent = new Parent();

		Field default_scoped_component_field = parent.getClass().getField("mDefaultScopedComponent");

		BindComponent bind_component_annotation = default_scoped_component_field.getAnnotation(BindComponent.class);

		assertNotNull(bind_component_annotation);

		assertTrue(BindComponent.Default.class.equals(bind_component_annotation.implementation()));
	}
	@Test
	public void binding()
	{
		Parent first_parent = new Parent();
		Parent second_parent = new Parent();

		assertNotNull("default scoped component binding", first_parent.geDefaultScopedComponent());
		assertNotNull("singleton scoped component binding", first_parent.getSingletonScopedComponent());

		assertTrue("default scope instance uniqueness", first_parent.geDefaultScopedComponent() != second_parent.geDefaultScopedComponent());
		assertTrue("instance scope instance uniqueness", first_parent.getSingletonScopedComponent() == second_parent.getSingletonScopedComponent());
	}

	@Test(expected = BindException.class)
	public void bindingFailure()
	{
		new FaultyParent();
	}
}
