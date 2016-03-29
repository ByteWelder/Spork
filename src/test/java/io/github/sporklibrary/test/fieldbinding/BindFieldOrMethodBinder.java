package io.github.sporklibrary.test.fieldbinding;

import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedMethod;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.binders.MethodBinder;

public class BindFieldOrMethodBinder implements FieldBinder<BindFieldOrMethod>, MethodBinder<BindFieldOrMethod>
{
	private int methodBindCount = 0;

	private int fieldBindCount = 0;

	@Override
	public Class<BindFieldOrMethod> getAnnotationClass()
	{
		return BindFieldOrMethod.class;
	}

	@Override
	public void bind(Object object, AnnotatedMethod<BindFieldOrMethod> annotatedMethod)
	{
		methodBindCount++;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindFieldOrMethod> annotatedField)
	{
		fieldBindCount++;
	}

	public int getMethodBindCount()
	{
		return methodBindCount;
	}

	public int getFieldBindCount()
	{
		return fieldBindCount;
	}
}
