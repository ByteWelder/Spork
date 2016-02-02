package io.github.sporklibrary.test.components;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.test.components.scope.DefaultImpliedScopeComponent;
import io.github.sporklibrary.test.components.scope.DefaultSpecifiedScopeComponent;
import io.github.sporklibrary.test.components.scope.FaultyConstructorComponent;
import io.github.sporklibrary.test.components.scope.SingletonScopeComponent;
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
		public DefaultImpliedScopeComponent mDefaultImpliedScopeComponent;

		@BindComponent
		public DefaultSpecifiedScopeComponent mDefaultSpecifiedScopeComponent;

		@BindComponent
		private SingletonScopeComponent mSingletonScopeComponent;

		public Parent()
		{
			Spork.bind(this);
		}

		public SingletonScopeComponent getSingletonScopedComponent()
		{
			return mSingletonScopeComponent;
		}

		public DefaultImpliedScopeComponent getDefaultImpliedScopeComponent()
		{
			return mDefaultImpliedScopeComponent;
		}

		public DefaultSpecifiedScopeComponent getDefaultSpecifiedScopeComponent()
		{
			return mDefaultSpecifiedScopeComponent;
		}
	}

	public static class FaultyConstructorParent
	{
		@BindComponent
		private FaultyConstructorComponent mFaultyConstructorComponent;

		public FaultyConstructorParent()
		{
			Spork.bind(this);
		}

		public FaultyConstructorComponent getFaultyComponent()
		{
			return mFaultyConstructorComponent;
		}
	}

	@Test
	public void defaultImpliedScopeClass() throws NoSuchFieldException
	{
		Parent parent = new Parent();

		Field default_scoped_component_field = parent.getClass().getField("mDefaultImpliedScopeComponent");

		BindComponent bind_component_annotation = default_scoped_component_field.getAnnotation(BindComponent.class);

		assertNotNull(bind_component_annotation);

		assertTrue(BindComponent.Default.class.equals(bind_component_annotation.implementation()));
	}

	@Test
	public void defaultSpecifiedScopeClass() throws NoSuchFieldException
	{
		Parent parent = new Parent();

		Field default_scoped_component_field = parent.getClass().getField("mDefaultSpecifiedScopeComponent");

		BindComponent bind_component_annotation = default_scoped_component_field.getAnnotation(BindComponent.class);

		assertNotNull(bind_component_annotation);

		assertTrue(BindComponent.Default.class.equals(bind_component_annotation.implementation()));
	}

	@Test
	public void binding()
	{
		Parent first_parent = new Parent();
		Parent second_parent = new Parent();

		assertNotNull("default implied scoped component binding", first_parent.getDefaultImpliedScopeComponent());
		assertNotNull("default specified scoped component binding", first_parent.getDefaultSpecifiedScopeComponent());
		assertNotNull("singleton scoped component binding", first_parent.getSingletonScopedComponent());

		assertTrue("default implied scope instance uniqueness", first_parent.getDefaultImpliedScopeComponent() != second_parent.getDefaultImpliedScopeComponent());
		assertTrue("default specified scope instance uniqueness", first_parent.getDefaultSpecifiedScopeComponent() != second_parent.getDefaultSpecifiedScopeComponent());
		assertTrue("instance scope instance uniqueness", first_parent.getSingletonScopedComponent() == second_parent.getSingletonScopedComponent());
	}

	@Test(expected = BindException.class)
	public void bindingFailure()
	{
		new FaultyConstructorParent();
	}
}
