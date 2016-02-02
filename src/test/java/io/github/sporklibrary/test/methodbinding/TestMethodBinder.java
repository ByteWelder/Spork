package io.github.sporklibrary.test.methodbinding;

import io.github.sporklibrary.reflection.AnnotatedMethod;
import io.github.sporklibrary.binders.MethodBinder;

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
