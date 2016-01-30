package io.github.sporklibrary.test.bindview.domain;

import android.os.Bundle;
import android.view.View;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindview.ViewProvider;

public class TestActivity extends android.app.Activity implements ViewProvider
{
	@BindView(R.id.viewbindingview)
	private View mViewBindingView;

	@BindView
	private TestView viewbindingview;

	@BindFragment(R.id.viewbindingfragment)
	private TestFragment mViewBindingFragment;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_binding);

		Spork.bind(this);
	}

	@Override
	public View getViewByIdSpecified()
	{
		return mViewBindingView;
	}

	@Override
	public View getViewByImplied()
	{
		return viewbindingview;
	}

	public TestView getViewBindingView()
	{
		return viewbindingview;
	}

	public TestFragment getViewBindingFragment()
	{
		return mViewBindingFragment;
	}
}
