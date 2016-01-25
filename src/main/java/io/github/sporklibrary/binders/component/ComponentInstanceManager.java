package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.Component;
import io.github.sporklibrary.binders.AnnotatedField;
import io.github.sporklibrary.exceptions.BindException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component instances for types that are annotated with the Component annotation.
 */
class ComponentInstanceManager
{
	private final Map<Class<?>, Object> mSingletonInstances = new HashMap<>();

	public Object getInstance(AnnotatedField<BindComponent> annotatedField, Object parent)
	{
		Class<?> field_target_class = getTargetClass(annotatedField);

		if (!annotatedField.getField().getType().isAssignableFrom(field_target_class))
		{
			throw new BindException(BindComponent.class, parent.getClass(), annotatedField.getField(), "incompatible type");
		}

		Component component_annotation = field_target_class.getAnnotation(Component.class);

		if (component_annotation == null)
		{
			throw new BindException(BindComponent.class, parent.getClass(), annotatedField.getField(), "no Component annotation found at target class");
		}

		switch (component_annotation.scope())
		{
			case DEFAULT:
				return create(field_target_class);

			case SINGLETON:
				Object instance = mSingletonInstances.get(field_target_class);
				return (instance != null) ? instance : createSingletonInstance(field_target_class);
		}

		throw new BindException(BindComponent.class, parent.getClass(), annotatedField.getField(), component_annotation.scope().toString() + "scope not supported");
	}

	private Class<?> getTargetClass(AnnotatedField<BindComponent> annotatedField)
	{
		Class<?> override_class = annotatedField.getAnnotation().implementation();

		if (override_class == BindComponent.Default.class)
		{
			return annotatedField.getField().getType();
		}
		else if (override_class != null)
		{
			return override_class;
		}
		else
		{
			throw new BindException(BindComponent.class, annotatedField.getField().getDeclaringClass(), annotatedField.getField(), "implementation class is null");
		}
	}

	private Object create(Class<?> classObject)
	{
		try
		{
			Constructor<?> constructor = classObject.getConstructor();

			if (constructor.isAccessible())
			{
				Object instance = constructor.newInstance();
				Spork.bind(instance);

				return instance;
			}
			else
			{
				constructor.setAccessible(true);
				Object instance = constructor.newInstance();
				Spork.bind(instance);
				constructor.setAccessible(false);

				return instance;
			}
		}
		catch (NoSuchMethodException e)
		{
			throw new BindException(Component.class, classObject, "no default constructor found for " + classObject.getName());
		}
		catch (InstantiationException e)
		{
			throw new BindException(Component.class, classObject, "failed to create instance of " + classObject.getName(), e);
		}
		catch (IllegalAccessException e)
		{
			throw new BindException(Component.class, classObject, "failed to create instance due to access restrictions for " + classObject.getName(), e);
		}
		catch (InvocationTargetException e)
		{
			throw new BindException(Component.class, classObject, "constructor threw exception for " + classObject.getName(), e);
		}
	}

	private synchronized Object createSingletonInstance(Class<?> classObject)
	{
		Object instance = create(classObject);

		mSingletonInstances.put(classObject, instance);

		return instance;
	}
}
