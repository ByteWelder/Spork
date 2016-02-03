package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.ComponentParent;

public class ComponentC
{
	private ComponentB mParent;

	public ComponentC(@ComponentParent ComponentB parent)
	{
		mParent = parent;

		Spork.bind(this);
	}

	public ComponentB getParent()
	{
		return mParent;
	}
}
