package io.github.sporklibrary.test.typebinding;

import io.github.sporklibrary.binders.TypeBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedType;

public class BindTypeBinder implements TypeBinder<BindValue>
{
	@Override
	public Class<BindValue> getAnnotationClass()
	{
		return BindValue.class;
	}

	@Override
	public void bind(Object object, AnnotatedType<BindValue> annotatedClass)
	{
		if (!IntSettable.class.isAssignableFrom(object.getClass()))
		{
			throw new BindException(BindValue.class, object.getClass(), "can only be used with IntSettable target");
		}

		IntSettable value_holder = (IntSettable)object;

		int value = annotatedClass.getAnnotation().value();
		value_holder.setValue(value);
	}
}
