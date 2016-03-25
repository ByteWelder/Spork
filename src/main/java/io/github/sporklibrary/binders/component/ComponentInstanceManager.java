package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentScope;
import io.github.sporklibrary.binders.component.factories.DefaultComponentFactory;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component instances for types that are annotated with the Component annotation.
 */
public class ComponentInstanceManager
{
	private final Map<Class<?>, Object> mSingletonInstances = new HashMap<>();

	private static ComponentFactory sComponentFactory = new DefaultComponentFactory();

	/**
	 * Override the default factory for mocking/stubbing purposes
	 * @param factory the ComponentFactory override
	 */
	public static void setComponentFactory(ComponentFactory factory)
	{
		sComponentFactory = factory;
	}

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
				return sComponentFactory.create(field_target_class, parent);
		}
	}

	/**
	 * Looks for the {@link ComponentScope} annotation and returns the {@link ComponentScope.Scope}
	 * when found or otherwise returns {@link ComponentScope.Scope.DEFAULT}.
	 * @param componentClass the class to check the scope for
	 * @return the scope for the specified class or otherwise {@link ComponentScope.Scope.DEFAULT}
	 */
	private ComponentScope.Scope getScope(Class<?> componentClass)
	{
		ComponentScope annotation = componentClass.getAnnotation(ComponentScope.class);

		return annotation != null ? annotation.value() : ComponentScope.Scope.DEFAULT;
	}

	/**
	 * Get the target class (the Field class) from the specified field.
	 * @param annotatedField the annotated field to process
	 * @return the Field type or the specified BindComponent override
	 */
	private Class<?> getTargetClass(AnnotatedField<BindComponent> annotatedField)
	{
		Class<?> override_class = annotatedField.getAnnotation().value();

		if (override_class == BindComponent.Default.class)
		{
			return annotatedField.getField().getType();
		}
		else // override class is never null per annotation design
		{
			return override_class;
		}
	}

	/**
	 * Create a new instance and register it in the cache.
	 * @param classObject the class to instantiate
	 * @return the created component instance
	 */
	private synchronized Object createSingletonInstance(Class<?> classObject)
	{
		Object instance = sComponentFactory.create(classObject, null);

		mSingletonInstances.put(classObject, instance);

		return instance;
	}
}
