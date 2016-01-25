package io.github.sporklibrary.components.shopping;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

public class ShoppingCart
{
	@BindComponent(implementation = IceCream.class)
	Dairy mDairy;

	public ShoppingCart()
	{
		Spork.bind(this);
	}

	public Dairy getDairy()
	{
		return mDairy;
	}
}
