package spork.test.typebinding;

import spork.exceptions.BindException;
import spork.interfaces.TypeBinder;

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
