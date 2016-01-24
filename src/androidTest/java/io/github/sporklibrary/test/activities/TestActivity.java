package io.github.sporklibrary.test.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.fragments.FragmentBindingFragment;
import io.github.sporklibrary.test.fragments.ViewBindingFragment;

public class TestActivity extends Activity
{
	@BindView
	private Button button_one;

	@BindView(R.id.button_two)
	private Button mButtonTwo;

	@BindFragment(R.id.viewbindingfragment)
	private ViewBindingFragment mViewBindingFragment;

	@BindFragment
	private FragmentBindingFragment fragmentbindingfragment;

	private boolean mClickedButtonOne = false;

	private boolean mClickedButtonTwo = false;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test);

		Spork.bind(this);
	}

	@BindClick(R.id.button_one)
	public void button_one()
	{
		mClickedButtonOne = true;
	}

	@BindClick(R.id.button_two)
	public void onClickButtonTwo(Button button)
	{
		mClickedButtonTwo = (button != null);
	}

	public Button getButtonByIdSpecified()
	{
		return mButtonTwo;
	}

	public Button getButtonByIdImplied()
	{
		return button_one;
	}

	public ViewBindingFragment getViewBindingFragment()
	{
		return mViewBindingFragment;
	}

	public FragmentBindingFragment getFragmentBindingFragment()
	{
		return fragmentbindingfragment;
	}

	public boolean hasClickedButtonOne()
	{
		return mClickedButtonOne;
	}

	public boolean hasClickedButtonTwo()
	{
		return mClickedButtonTwo;
	}
}
