package io.github.sporklibrary.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindresource.ResourceProvider;

import javax.annotation.Nullable;

public class TestActivity extends android.app.Activity implements ResourceProvider
{
	@BindResource(R.string.app_name)
	private String mAppName;

	@BindResource
	private String app_name;

	@BindResource(R.dimen.spork_test_dimension)
	private float mSporkTestDimension;

	@BindResource
	private Float spork_test_dimension;

	@BindResource(R.drawable.spork_test_drawable)
	private Drawable mSporkTestDrawable;

	@BindResource
	private Drawable spork_test_drawable;

	@BindFragment(R.id.resourcebindingfragment)
	private TestFragment mResourceBindingFragment;

	@BindView(R.id.resourcebindingview)
	private TestView mResourceBindingView;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_resource_binding);

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

	public TestFragment getResourceBindingFragment()
	{
		return mResourceBindingFragment;
	}

	public TestView getResourceBindingView()
	{
		return mResourceBindingView;
	}
}
