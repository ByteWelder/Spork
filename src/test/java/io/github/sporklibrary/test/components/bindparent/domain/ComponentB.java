package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.ComponentParent;

public class ComponentB
{
	@BindComponent
	private ComponentC mChild;

	private ComponentA mParent;

	public ComponentB(@ComponentParent ComponentA parent)
	{
		mParent = parent;

		Spork.bind(this);
	}

	public ComponentC getChild()
	{
		return mChild;
	}

	public ComponentA getParent()
	{
		return mParent;
	}
}
