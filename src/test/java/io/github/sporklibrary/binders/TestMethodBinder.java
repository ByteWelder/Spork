package io.github.sporklibrary.binders;

import io.github.sporklibrary.annotations.BindMethodTest;

public class TestMethodBinder implements MethodBinder<BindMethodTest>
{
	private int mMethodCount = 0;

	@Override
	public Class<BindMethodTest> getAnnotationClass()
	{
		return BindMethodTest.class;
	}

	@Override
	public void bind(Object object, AnnotatedMethod<BindMethodTest> annotatedField)
	{
		mMethodCount++;
	}

	public int getMethodCount()
	{
		return mMethodCount;
	}
}
