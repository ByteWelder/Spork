package io.github.sporklibrary.test.views;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;

public class BindClickPojo
{
	public BindClickPojo()
	{
		Spork.bind(this);
	}

	@BindClick
	private void onClick()
	{
	}
}
