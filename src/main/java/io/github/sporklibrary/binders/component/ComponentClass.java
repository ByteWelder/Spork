package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.annotations.Component;

class ComponentClass
{
	private final Component mComponent;

	private final Class<?> mDeclaredClass;

	public ComponentClass(Component component, Class<?> declaredClass)
	{
		mComponent = component;
		mDeclaredClass = declaredClass;
	}

	public Component getComponent()
	{
		return mComponent;
	}

	public Class<?> getDeclaredClass()
	{
		return mDeclaredClass;
	}
}
