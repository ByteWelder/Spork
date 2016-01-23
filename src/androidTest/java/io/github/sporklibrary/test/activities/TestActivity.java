package io.github.sporklibrary.test.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.InjectView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.fragments.TestFragment;

public class TestActivity extends Activity
{
	@InjectView(R.id.button)
	private Button mButton;

	@InjectView
	private Button button;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test);
		Spork.inject(this);
	}

	public Button getButtonById()
	{
		return mButton;
	}

	public Button getButtonByName()
	{
		return button;
	}

	public TestFragment getTestFragment()
	{
		return (TestFragment)getFragmentManager().findFragmentById(R.id.testfragment);
	}
}
