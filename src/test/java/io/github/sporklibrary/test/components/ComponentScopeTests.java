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
		public DefaultImpliedScopeComponent defaultImpliedScopeComponent;

		@BindComponent
		public DefaultSpecifiedScopeComponent defaultSpecifiedScopeComponent;

		@BindComponent
		private SingletonScopeComponent singletonScopeComponent;

		public Parent()
		{
			Spork.bind(this);
		}

		public SingletonScopeComponent getSingletonScopedComponent()
		{
			return singletonScopeComponent;
		}

		public DefaultImpliedScopeComponent getDefaultImpliedScopeComponent()
		{
			return defaultImpliedScopeComponent;
		}

		public DefaultSpecifiedScopeComponent getDefaultSpecifiedScopeComponent()
		{
			return defaultSpecifiedScopeComponent;
		}
	}

	public static class FaultyConstructorParent
	{
		@BindComponent
		private FaultyConstructorComponent faultyConstructorComponent;

		public FaultyConstructorParent()
		{
			Spork.bind(this);
		}

		public FaultyConstructorComponent getFaultyComponent()
		{
			return faultyConstructorComponent;
		}
	}

	@Test
	public void defaultImpliedScopeClass() throws NoSuchFieldException
	{
		Parent parent = new Parent();

		Field defaultScopedComponentField = parent.getClass().getField("defaultImpliedScopeComponent");

		BindComponent bindComponentAnnotation = defaultScopedComponentField.getAnnotation(BindComponent.class);

		assertNotNull(bindComponentAnnotation);

		assertTrue(BindComponent.Default.class.equals(bindComponentAnnotation.value()));
	}

	@Test
	public void defaultSpecifiedScopeClass() throws NoSuchFieldException
	{
		Parent parent = new Parent();

		Field defaultScopedComponentField = parent.getClass().getField("defaultSpecifiedScopeComponent");

		BindComponent bindComponentAnnotation = defaultScopedComponentField.getAnnotation(BindComponent.class);

		assertNotNull(bindComponentAnnotation);

		assertTrue(BindComponent.Default.class.equals(bindComponentAnnotation.value()));
	}

	@Test
	public void binding()
	{
		Parent firstParent = new Parent();
		Parent secondParent = new Parent();

		assertNotNull("default implied scoped component binding", firstParent.getDefaultImpliedScopeComponent());
		assertNotNull("default specified scoped component binding", firstParent.getDefaultSpecifiedScopeComponent());
		assertNotNull("singleton scoped component binding", firstParent.getSingletonScopedComponent());

		assertTrue("default implied scope instance uniqueness", firstParent.getDefaultImpliedScopeComponent() != secondParent.getDefaultImpliedScopeComponent());
		assertTrue("default specified scope instance uniqueness", firstParent.getDefaultSpecifiedScopeComponent() != secondParent.getDefaultSpecifiedScopeComponent());
		assertTrue("instance scope instance uniqueness", firstParent.getSingletonScopedComponent() == secondParent.getSingletonScopedComponent());
	}

	@Test(expected = BindException.class)
	public void bindingFailure()
	{
		new FaultyConstructorParent();
	}
}
