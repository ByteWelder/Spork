package io.github.sporklibrary.test.components.mocking.domain;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class ParentComponent
{
	@BindComponent(RegularImplementation.class)
	private CommonInterface commonInterface;

	public ParentComponent()
	{
		Spork.bind(this);
	}

	public CommonInterface getCommonInterface()
	{
		return commonInterface;
	}
}
