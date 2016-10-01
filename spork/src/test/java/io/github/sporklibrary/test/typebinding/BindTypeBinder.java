package io.github.sporklibrary.test.typebinding;

import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.TypeBinder;

public class BindTypeBinder implements TypeBinder<BindValue> {

	@Override
	public void bind(Object instance, BindValue annotation, Class<?> annotatedType, Object[] modules) {
		if (!IntSettable.class.isAssignableFrom(instance.getClass())) {
			throw new BindException(BindValue.class, instance.getClass(), "can only be used with IntSettable target");
		}

		IntSettable valueHolder = (IntSettable) instance;
		int value = annotation.value();
		valueHolder.setValue(value);
	}

	@Override
	public Class<BindValue> getAnnotationClass() {
		return BindValue.class;
	}
}
