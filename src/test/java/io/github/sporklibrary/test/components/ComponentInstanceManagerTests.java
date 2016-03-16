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
		public DefaultSpecifiedScopeComponent mDefaultSpecifiedScopeComponent;

		@BindComponent
		public DefaultImpliedScopeComponent mDefaultImpliedScopeComponent;

		@BindComponent
		public SingletonScopeComponent mSingletonScopeComponent;

		public Parent()
		{
			Spork.bind(this);
		}
	}

	public static class FaultyConstructorParent
	{
		@BindComponent
		private FaultyConstructorComponent mChild;

		public FaultyConstructorParent()
		{
			Spork.bind(this);
		}
	}

	public static class FaultyInstantiationParent
	{
		@BindComponent
		public FaultyInstantiationComponent mChild;

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

		Field default_implied_field = parent.getClass().getField("mDefaultImpliedScopeComponent");
		Field default_specified_field = parent.getClass().getField("mDefaultSpecifiedScopeComponent");
		Field singleton_field = parent.getClass().getField("mSingletonScopeComponent");

		BindComponent default_implied_annotation = default_implied_field.getAnnotation(BindComponent.class);
		BindComponent default_specified_annotation = default_specified_field.getAnnotation(BindComponent.class);
		BindComponent singleton_annotation = singleton_field.getAnnotation(BindComponent.class);

		assertNotNull(default_implied_annotation);
		assertNotNull(default_specified_annotation);
		assertNotNull(singleton_annotation);

		AnnotatedField<BindComponent> default_implied_annotated_field = new AnnotatedField<>(default_implied_annotation, default_implied_field);
		AnnotatedField<BindComponent> default_specified_annotated_field = new AnnotatedField<>(default_specified_annotation, default_specified_field);
		AnnotatedField<BindComponent> singleton_annotated_field = new AnnotatedField<>(singleton_annotation, singleton_field);

		Object default_implied_instance = manager.getInstance(parent, default_implied_annotated_field);
		Object default_specified_instance = manager.getInstance(parent, default_specified_annotated_field);
		Object singleton_instance = manager.getInstance(parent, singleton_annotated_field);
		Object singleton_instance_copy = manager.getInstance(parent, singleton_annotated_field);

		assertNotNull(default_implied_instance);
		assertNotNull(default_specified_instance);
		assertNotNull(singleton_instance);
		assertNotNull(singleton_instance_copy);

		assertTrue(DefaultImpliedScopeComponent.class.isAssignableFrom(default_implied_instance.getClass()));
		assertTrue(DefaultSpecifiedScopeComponent.class.isAssignableFrom(default_specified_instance.getClass()));
		assertTrue(SingletonScopeComponent.class.isAssignableFrom(singleton_instance.getClass()));
		assertTrue(SingletonScopeComponent.class.isAssignableFrom(singleton_instance_copy.getClass()));

		assertTrue(singleton_instance == singleton_instance_copy);
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
