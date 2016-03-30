package io.github.sporklibrary.test.components.shopping;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class FaultyShoppingCart
{
	@BindComponent(IceCream.class)
	private String name;

	public FaultyShoppingCart()
	{
		Spork.bind(this);
	}

	public String getName()
	{
		return name;
	}
}
