package io.github.sporklibrary.component;

import io.github.sporklibrary.AnnotatedField;
import io.github.sporklibrary.FieldInjector;
import io.github.sporklibrary.annotations.InjectComponent;

import java.lang.reflect.Field;

/**
 * The default FieldInjector that injects field annotated with the Inject annotation.
 */
public class ComponentFieldInjector implements FieldInjector<InjectComponent>
{
	private final ComponentInstanceManager mComponentInstanceManager = new ComponentInstanceManager();

	@Override
	public Class<InjectComponent> getAnnotationClass()
	{
		return InjectComponent.class;
	}

	@Override
	public void inject(Object object, AnnotatedField<InjectComponent> annotatedField)
	{
		Object instance = mComponentInstanceManager.getInstance(annotatedField.getField(), object);

		try
		{
			Field field = annotatedField.getField();

			boolean field_accessible = field.isAccessible();
			field.setAccessible(true);
			field.set(object, instance);
			field.setAccessible(field_accessible);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
