package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.Component;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.exceptions.BindException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages Component instances for types that are annotated with the Component annotation.
 */
class ComponentInstanceManager
{
	private final Map<String, ComponentRetriever> mPackageComponentRetrieverMap = new HashMap<>(1);

	private final Map<Class<?>, Object> mSingletonInstances = new HashMap<>();

	public Object getInstance(Field field, Object parent)
	{
		Package pkg = parent.getClass().getPackage();

		ComponentRetriever component_retriever = getComponentRetriever(pkg);

		Class<?> field_target_type = field.getType();

		ComponentClass component_class = component_retriever.get(field_target_type);

		if (component_class != null)
		{
			switch (component_class.getComponent().scope())
			{
				case DEFAULT:
					return create(field_target_type);

				case SINGLETON:
					Object instance = mSingletonInstances.get(field_target_type);
					return (instance != null) ? instance : createSingletonInstance(field_target_type);
			}
		}

		throw new BindException(BindComponent.class, parent.getClass(), "no Component annotation found for " + field_target_type.getName() + " in " + pkg);
	}

	private ComponentRetriever getComponentRetriever(Package pkg)
	{
		String package_name = pkg.getName();

		ComponentRetriever component_retriever;

		synchronized (mPackageComponentRetrieverMap)
		{
			component_retriever = mPackageComponentRetrieverMap.get(package_name);

			// insert new one into map
			if (component_retriever == null)
			{
				component_retriever = new ComponentRetriever(pkg);
				mPackageComponentRetrieverMap.put(package_name, component_retriever);
			}
		}

		return component_retriever;
	}

	private Object create(Class<?> classObject)
	{
		// TODO: bind for new instance
		try
		{
			Constructor<?> constructor = classObject.getConstructor();

			boolean ctor_accessible = constructor.isAccessible();
			constructor.setAccessible(true);
			Object instance = constructor.newInstance();
			constructor.setAccessible(ctor_accessible);

			return instance;
		}
		catch (NoSuchMethodException e)
		{
			throw new BindException(Component.class, classObject, "no default constructor found");
		}
		catch (InstantiationException e)
		{
			throw new BindException(Component.class, classObject, "failed to create instance", e);
		}
		catch (IllegalAccessException e)
		{
			throw new BindException(Component.class, classObject, "failed to create instance due to access restrictions", e);
		}
		catch (InvocationTargetException e)
		{
			throw new BindException(Component.class, classObject, "constructor threw exception", e);
		}
	}

	private synchronized Object createSingletonInstance(Class<?> classObject)
	{
		Object instance = create(classObject);

		mSingletonInstances.put(classObject, instance);

		return instance;
	}
}
