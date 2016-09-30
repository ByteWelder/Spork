package io.github.sporklibrary.test.fieldbinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.interfaces.MethodBinder;

public class BindFieldOrMethodBinder implements FieldBinder<BindFieldOrMethod>, MethodBinder<BindFieldOrMethod> {
	private int methodBindCount = 0;
	private int fieldBindCount = 0;

	@Override
	public void bind(Object instance, BindFieldOrMethod annotation, Field field, @Nullable Object[] modules) {
		fieldBindCount++;
	}

	@Override
	public void bind(Object instance, BindFieldOrMethod annotation, Method method, @Nullable Object[] modules) {
		methodBindCount++;
	}

	@Override
	public Class<BindFieldOrMethod> getAnnotationClass() {
		return BindFieldOrMethod.class;
	}

	public int getMethodBindCount() {
		return methodBindCount;
	}

	public int getFieldBindCount() {
		return fieldBindCount;
	}
}
