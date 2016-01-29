package io.github.sporklibrary.test.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.fragments.FragmentBindingFragment;
import io.github.sporklibrary.test.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class Api17TestActivity extends Activity
{
	@BindFragment
	private FragmentBindingFragment fragmentbindingfragment;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_child_fragment);

		Spork.bind(this);
	}

	public FragmentBindingFragment getFragmentBindingFragment()
	{
		return fragmentbindingfragment;
	}
}
