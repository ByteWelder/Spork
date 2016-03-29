package io.github.sporklibrary.test.components;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.binders.component.ComponentInstanceManager;
import io.github.sporklibrary.test.components.scope.*;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentInstanceManagerTests
{
	// Fields are public for easy test access
	public static class Parent
	{
		@BindComponent
		public DefaultSpecifiedScopeComponent defaultSpecifiedScopeComponent;

		@BindComponent
		public DefaultImpliedScopeComponent defaultImpliedScopeComponent;

		@BindComponent
		public SingletonScopeComponent dingletonScopeComponent;

		public Parent()
		{
			Spork.bind(this);
		}
	}

	public static class FaultyConstructorParent
	{
		@BindComponent
		private FaultyConstructorComponent child;

		public FaultyConstructorParent()
		{
			Spork.bind(this);
		}
	}

	public static class FaultyInstantiationParent
	{
		@BindComponent
		public FaultyInstantiationComponent child;

		public FaultyInstantiationParent()
		{
			Spork.bind(this);
		}
	}

	@Test
	public void componentInstanceManager() throws NoSuchFieldException
	{
		ComponentInstanceManager manager = new ComponentInstanceManager();

		Parent parent = new Parent();

		Field defaultImpliedField = parent.getClass().getField("defaultImpliedScopeComponent");
		Field defaultSpecifiedField = parent.getClass().getField("defaultSpecifiedScopeComponent");
		Field singletonField = parent.getClass().getField("dingletonScopeComponent");

		BindComponent defaultImpliedAnnotation = defaultImpliedField.getAnnotation(BindComponent.class);
		BindComponent defaultSpecifiedAnnotation = defaultSpecifiedField.getAnnotation(BindComponent.class);
		BindComponent singletonAnnotation = singletonField.getAnnotation(BindComponent.class);

		assertNotNull(defaultImpliedAnnotation);
		assertNotNull(defaultSpecifiedAnnotation);
		assertNotNull(singletonAnnotation);

		AnnotatedField<BindComponent> defaultImpliedAnnotatedField = new AnnotatedField<>(defaultImpliedAnnotation, defaultImpliedField);
		AnnotatedField<BindComponent> defaultSpecifiedAnnotatedField = new AnnotatedField<>(defaultSpecifiedAnnotation, defaultSpecifiedField);
		AnnotatedField<BindComponent> singletonAnnotatedField = new AnnotatedField<>(singletonAnnotation, singletonField);

		Object defaultImpliedInstance = manager.getInstance(parent, defaultImpliedAnnotatedField);
		Object defaultSpecifiedInstance = manager.getInstance(parent, defaultSpecifiedAnnotatedField);
		Object singletonInstance = manager.getInstance(parent, singletonAnnotatedField);
		Object singletonInstanceCopy = manager.getInstance(parent, singletonAnnotatedField);

		assertNotNull(defaultImpliedInstance);
		assertNotNull(defaultSpecifiedInstance);
		assertNotNull(singletonInstance);
		assertNotNull(singletonInstanceCopy);

		assertTrue(DefaultImpliedScopeComponent.class.isAssignableFrom(defaultImpliedInstance.getClass()));
		assertTrue(DefaultSpecifiedScopeComponent.class.isAssignableFrom(defaultSpecifiedInstance.getClass()));
		assertTrue(SingletonScopeComponent.class.isAssignableFrom(singletonInstance.getClass()));
		assertTrue(SingletonScopeComponent.class.isAssignableFrom(singletonInstanceCopy.getClass()));

		assertTrue(singletonInstance == singletonInstanceCopy);
	}

	@Test(expected = BindException.class)
	public void faultyComponentConstructor()
	{
		new FaultyConstructorParent();
	}

	@Test(expected = BindException.class)
	public void faultyInstantiationConstructor()
	{
		new FaultyInstantiationParent();
	}
}
