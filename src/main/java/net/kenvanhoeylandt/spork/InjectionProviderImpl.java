package net.kenvanhoeylandt.spork;

import net.kenvanhoeylandt.spork.annotations.Inject;
import net.kenvanhoeylandt.spork.component.ComponentInstanceManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * The default InjectionProvider that injects field annotated with the Inject annotation.
 */
public class InjectionProviderImpl implements InjectionProvider
{
	private final ComponentInstanceManager mComponentInstanceManager = new ComponentInstanceManager();

	@Override
	public Class<? extends Annotation> getAnnotationClass()
	{
		return Inject.class;
	}

	@Override
	public void inject(Object object, AnnotatedField<?> annotatedField)
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
