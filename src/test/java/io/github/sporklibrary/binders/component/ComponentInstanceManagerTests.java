package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.Component;
import io.github.sporklibrary.binders.AnnotatedField;
import io.github.sporklibrary.components.scope.DefaultScopedComponent;
import io.github.sporklibrary.components.scope.SingletonScopedComponent;
import io.github.sporklibrary.exceptions.BindException;
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
		public DefaultScopedComponent mDefaultScopedComponent;

		@BindComponent
		public SingletonScopedComponent mSingletonScopedComponent;

		public Parent()
		{
			Spork.bind(this);
		}
	}

	public static class FaultyConstructorParent
	{
		@BindComponent
		FaultyConstructorComponent mChild;

		public FaultyConstructorParent()
		{
			Spork.bind(this);
		}
	}

	@Component
	public static class FaultyConstructorComponent
	{
		private final Object mObject;

		public FaultyConstructorComponent(Object object)
		{
			mObject = object;
		}
	}


	public static class FaultyInstantiationParent
	{
		@BindComponent
		FaultyInstantiationComponent mChild;

		public FaultyInstantiationParent()
		{
			Spork.bind(this);
		}
	}

	@Component
	public static class FaultyInstantiationComponent
	{
		public FaultyInstantiationComponent()
		{
			throw new RuntimeException("faulty instantiation");
		}
	}

	@Test
	public void componentInstanceManager() throws NoSuchFieldException
	{
		ComponentInstanceManager manager = new ComponentInstanceManager();

		Parent parent = new Parent();

		Field default_field = parent.getClass().getField("mDefaultScopedComponent");
		Field singleton_field = parent.getClass().getField("mSingletonScopedComponent");

		BindComponent default_annotation = default_field.getAnnotation(BindComponent.class);
		BindComponent singelton_annotation = default_field.getAnnotation(BindComponent.class);

		assertNotNull(default_annotation);
		assertNotNull(singelton_annotation);

		AnnotatedField<BindComponent> default_annotated_field = new AnnotatedField<>(default_annotation, default_field);
		AnnotatedField<BindComponent> singleton_annotated_field = new AnnotatedField<>(singelton_annotation, singleton_field);

		Object default_instance = manager.getInstance(default_annotated_field, parent);
		Object singleton_instance = manager.getInstance(singleton_annotated_field, parent);
		Object singleton_instance_copy = manager.getInstance(singleton_annotated_field, parent);

		assertNotNull(default_instance);
		assertNotNull(singleton_instance);
		assertNotNull(singleton_instance_copy);

		assertTrue(DefaultScopedComponent.class.isAssignableFrom(default_instance.getClass()));
		assertTrue(SingletonScopedComponent.class.isAssignableFrom(singleton_instance.getClass()));
		assertTrue(SingletonScopedComponent.class.isAssignableFrom(singleton_instance_copy.getClass()));

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
