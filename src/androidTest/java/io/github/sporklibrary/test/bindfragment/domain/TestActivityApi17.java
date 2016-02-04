package io.github.sporklibrary.test.bindfragment.domain;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindfragment.FragmentProvider;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class TestActivityApi17 extends Activity implements FragmentProvider
{
	@BindFragment(R.id.testfragmentapi17)
	private TestFragmentApi17 mTestFragment;

	@BindFragment
	private TestFragmentApi17 testfragmentapi17;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fragment_binding_api17);

		Spork.bind(this);
	}

	@Override
	public Fragment getFragmentByIdSpecified()
	{
		return mTestFragment;
	}

	@Override
	public Fragment getFragmentByIdImplied()
	{
		return testfragmentapi17;
	}


	public TestFragmentApi17 getTestFragment()
	{
		return testfragmentapi17;
	}
}
