package io.github.sporklibrary.binders;

import io.github.sporklibrary.annotations.BindFieldOrMethod;

public class TestFieldAndMethodBinder implements FieldBinder<BindFieldOrMethod>, MethodBinder<BindFieldOrMethod>
{
	private int mMethodBindCount = 0;

	private int mFieldBindCount = 0;

	@Override
	public Class<BindFieldOrMethod> getAnnotationClass()
	{
		return BindFieldOrMethod.class;
	}

	@Override
	public void bind(Object object, AnnotatedMethod<BindFieldOrMethod> annotatedMethod)
	{
		mMethodBindCount++;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindFieldOrMethod> annotatedField)
	{
		mFieldBindCount++;
	}

	public int getMethodBindCount()
	{
		return mMethodBindCount;
	}

	public int getFieldBindCount()
	{
		return mFieldBindCount;
	}
}
