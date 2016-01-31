package io.github.sporklibrary.test.bindclick.domain;

import android.app.Activity;
import android.os.Bundle;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.bindclick.ClickTestProvider;
import io.github.sporklibrary.test.R;

public class TestActivity extends Activity implements ClickTestProvider
{
	@BindFragment(R.id.testfragment)
	private TestFragment mTestFragment;

	@BindView(R.id.testview)
	private TestView mTestView;

	private int mClickCount = 0;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_click_binding);

		Spork.bind(this);
	}

	@BindClick(R.id.click_binding_activity_button)
	private void onClick()
	{
		mClickCount++;
	}

	@Override
	public int getClickCount()
	{
		return mClickCount;
	}

	@Override
	public int getClickViewResourceId()
	{
		return R.id.click_binding_activity_button;
	}

	public TestFragment getTestFragment()
	{
		return mTestFragment;
	}

	public TestView getTestView()
	{
		return mTestView;
	}
}
