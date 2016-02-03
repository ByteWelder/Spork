package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentParent;
import io.github.sporklibrary.annotations.ComponentScope;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.NotSupportedException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.exceptions.BindException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component instances for types that are annotated with the Component annotation.
 */
public class ComponentInstanceManager
{
	private final Map<Class<?>, Object> mSingletonInstances = new HashMap<>();

	/**
	 * Gets an instance of a component within its scope.
	 * The scope is either singleton or default.
	 * If the scope is unsupported, default is assumed.
	 * @param annotatedField the annotated field to get an instance for
	 * @param parent the parent object that holds the field
	 * @return the component instance
	 */
	public Object getInstance(Object parent, AnnotatedField<BindComponent> annotatedField)
	{
		Class<?> field_target_class = getTargetClass(annotatedField);

		if (!annotatedField.getField().getType().isAssignableFrom(field_target_class))
		{
			throw new BindException(BindComponent.class, parent.getClass(), annotatedField.getField(), "incompatible type");
		}

		ComponentScope.Scope scope = getScope(field_target_class);

		switch (scope)
		{
			case SINGLETON:
				Object instance = mSingletonInstances.get(field_target_class);
				return (instance != null) ? instance : createSingletonInstance(field_target_class);

			case DEFAULT:
			default:
				return create(field_target_class, parent);
		}
	}

	public ComponentScope.Scope getScope(Class<?> componentClass)
	{
		ComponentScope annotation = componentClass.getAnnotation(ComponentScope.class);

		return annotation != null ? annotation.value() : ComponentScope.Scope.DEFAULT;
	}

	private Class<?> getTargetClass(AnnotatedField<BindComponent> annotatedField)
	{
		Class<?> override_class = annotatedField.getAnnotation().implementation();

		if (override_class == BindComponent.Default.class)
		{
			return annotatedField.getField().getType();
		}
		else // override class is never null per annotation design
		{
			return override_class;
		}
	}

	private Object create(Class<?> classObject, @Nullable Object parent)
	{
		try
		{
			if (classObject.getConstructors().length != 1)
			{
				throw new BindException(BindComponent.class, classObject, "components must have exactly 1 public constructor (explicit or implied)");
			}

			Constructor<?> constructor = classObject.getConstructors()[0];

			boolean is_accessible = constructor.isAccessible();

			// ensure constructor can be invoked
			if (!is_accessible)
			{
				constructor.setAccessible(true);
			}

			Object[] constructor_args = getConstructorArguments(constructor, parent);

			Object instance = constructor.newInstance(constructor_args);

			// reset accessibility
			if (!is_accessible)
			{
				constructor.setAccessible(false);
			}

			// Bind recursively
			Spork.getBinderManager().bind(instance);

			return instance;
		}
		catch (InvocationTargetException e)
		{
			throw new BindException(BindComponent.class, classObject, "constructor threw exception", e);
		}
		catch (Exception e)
		{
			throw new BindException(BindComponent.class, classObject, "failed to create instance", e);
		}
	}

	private Object[] getConstructorArguments(Constructor<?> constructor, @Nullable Object parent)
	{
		Parameter[] parameters = constructor.getParameters();

		if (parameters.length == 0)
		{
			return new Object[0];
		}
		else if (parameters.length == 1)
		{
			Parameter parameter = constructor.getParameters()[0];

			ComponentParent annotation = parameter.getAnnotation(ComponentParent.class);

			if (annotation == null)
			{
				throw new BindException(BindComponent.class, "component constructor has an invalid parameter or the constructor parameter is missing the @ComponentParent annotation");
			}

			if (parent == null)
			{
				throw new BindException(BindComponent.class, "@ComponentParent only works with default-scoped components");
			}

			if (!parameter.getType().isAssignableFrom(parent.getClass()))
			{
				throw new BindException(BindComponent.class, "@ComponentParent target type is not compatible with the actual parent type");
			}

			return new Object[] { parent };
		}
		else
		{
			throw new NotSupportedException("component constructor must have 0 or 1 attributes");
		}
	}

	private synchronized Object createSingletonInstance(Class<?> classObject)
	{
		Object instance = create(classObject, null);

		mSingletonInstances.put(classObject, instance);

		return instance;
	}
}
