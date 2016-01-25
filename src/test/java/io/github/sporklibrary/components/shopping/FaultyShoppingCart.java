package io.github.sporklibrary.components.shopping;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class FaultyShoppingCart
{
	@BindComponent(implementation = IceCream.class)
	String mName;

	public FaultyShoppingCart()
	{
		Spork.bind(this);
	}

	public String getName()
	{
		return mName;
	}
}
