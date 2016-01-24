package io.github.sporklibrary.component;

import io.github.sporklibrary.AnnotatedField;
import io.github.sporklibrary.FieldBinder;
import io.github.sporklibrary.annotations.BindComponent;

import java.lang.reflect.Field;

/**
 * The default FieldBinder that binds field annotated with the Bind annotation.
 */
public class ComponentFieldBinder implements FieldBinder<BindComponent>
{
	private final ComponentInstanceManager mComponentInstanceManager = new ComponentInstanceManager();

	@Override
	public Class<BindComponent> getAnnotationClass()
	{
		return BindComponent.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindComponent> annotatedField)
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
