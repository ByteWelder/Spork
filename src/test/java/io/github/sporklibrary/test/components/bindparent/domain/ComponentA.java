package io.github.sporklibrary.test.components.bindparent.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class ComponentA
{
	@BindComponent
	private ComponentB mChild;

	public ComponentA()
	{
		Spork.bind(this);
	}

	public ComponentB getChild()
	{
		return mChild;
	}
}
