package io.github.sporklibrary.test.bindresource.domain;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.test.bindresource.ResourceProvider;

import javax.annotation.Nullable;

public class TestView extends android.view.View implements ResourceProvider
{
	@BindResource(io.github.sporklibrary.test.R.string.app_name)
	private String mAppName;

	@BindResource(io.github.sporklibrary.test.R.string.app_name)
	private String app_name;

	@BindResource(io.github.sporklibrary.test.R.dimen.spork_test_dimension)
	private float mSporkTestDimension;

	@BindResource
	private Float spork_test_dimension;

	@BindResource(io.github.sporklibrary.test.R.drawable.spork_test_drawable)
	private Drawable mSporkTestDrawable;

	@BindResource
	private Drawable spork_test_drawable;

	public TestView(Context context)
	{
		super(context);
		init();
	}

	public TestView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public TestView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init()
	{
		Spork.bind(this);
	}

	@Nullable
	@Override
	public String getStringByIdSpecified()
	{
		return mAppName;
	}

	@Nullable
	@Override
	public String getStringByIdImplied()
	{
		return app_name;
	}

	@Nullable
	@Override
	public Drawable getDrawableByIdSpecified()
	{
		return mSporkTestDrawable;
	}

	@Nullable
	@Override
	public Drawable getDrawableByIdImplied()
	{
		return spork_test_drawable;
	}

	@Override
	public float getDimensionByIdSpecified()
	{
		return mSporkTestDimension;
	}

	@Override
	public float getDimensionByIdImplied()
	{
		return spork_test_dimension;
	}
}
