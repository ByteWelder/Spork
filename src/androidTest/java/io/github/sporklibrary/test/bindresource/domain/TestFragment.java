package io.github.sporklibrary.test.bindresource.domain;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.test.bindresource.ResourceProvider;

public class TestFragment extends android.app.Fragment implements ResourceProvider
{
	@BindResource(io.github.sporklibrary.test.R.string.app_name)
	private String mAppName;

	@BindResource
	private String app_name;

	@BindResource(io.github.sporklibrary.test.R.dimen.spork_test_dimension)
	private float mSporkTestDimension;

	@BindResource
	private Float spork_test_dimension;

	@BindResource(io.github.sporklibrary.test.R.drawable.spork_test_drawable)
	private Drawable mSporkTestDrawable;

	@BindResource
	private Drawable spork_test_drawable;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		return new View(getActivity());
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		Spork.bind(this);
	}

	@javax.annotation.Nullable
	@Override
	public String getStringByIdSpecified()
	{
		return mAppName;
	}

	@javax.annotation.Nullable
	@Override
	public String getStringByIdImplied()
	{
		return app_name;
	}

	@javax.annotation.Nullable
	@Override
	public Drawable getDrawableByIdSpecified()
	{
		return mSporkTestDrawable;
	}

	@javax.annotation.Nullable
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
