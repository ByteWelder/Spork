package io.github.sporklibrary.test.contextprovider.domain;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.annotations.ComponentParent;
import io.github.sporklibrary.interfaces.ContextProvider;
import io.github.sporklibrary.test.R;

public class TestComponent implements ContextProvider
{
	private final Activity mActivity;

	@BindResource(R.drawable.spork_test_drawable)
	private Drawable mDrawable;

	public TestComponent(@ComponentParent Activity activity)
	{
		mActivity = activity;

		Spork.bind(this);
	}

	@Override
	public Context getContext()
	{
		return mActivity;
	}

	public Drawable getDrawable()
	{
		return mDrawable;
	}
}