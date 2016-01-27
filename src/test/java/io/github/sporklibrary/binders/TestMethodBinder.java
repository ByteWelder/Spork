package io.github.sporklibrary.binders;

import io.github.sporklibrary.annotations.BindMethod;

public class TestMethodBinder implements MethodBinder<BindMethod>
{
	private int mMethodCount = 0;

	@Override
	public Class<BindMethod> getAnnotationClass()
	{
		return BindMethod.class;
	}

	@Override
	public void bind(Object object, AnnotatedMethod<BindMethod> annotatedField)
	{
		mMethodCount++;
	}

	public int getMethodCount()
	{
		return mMethodCount;
	}
}
