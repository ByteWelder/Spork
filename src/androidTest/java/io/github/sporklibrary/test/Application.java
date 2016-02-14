package io.github.sporklibrary.test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.BindResource;

public class Application extends android.app.Application
{
	private static Application sInstance = null;

	public static class Component
	{
	}

	@BindResource
	private Float spork_test_dimension;

	@BindComponent
	private Component mComponent;

	@Override
	public void onCreate()
	{
		super.onCreate();

		sInstance = this;

		Spork.bind(this);
	}

	public Float getTestDimension()
	{
		return spork_test_dimension;
	}

	public Component getComponent()
	{
		return mComponent;
	}

	public static Application getInstance()
	{
		return sInstance;
	}
}
