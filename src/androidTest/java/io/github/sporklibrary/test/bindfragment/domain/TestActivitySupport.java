package io.github.sporklibrary.test.bindfragment.domain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.R;

public class TestActivitySupport extends AppCompatActivity
{
	@BindFragment(R.id.testfragment)
	private TestFragmentSupport mFragment;

	@BindFragment
	private TestFragmentSupport testfragment;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fragment_binding_support);

		Spork.bind(this);
	}

	public Fragment getFragmentByIdSpecified()
	{
		return mFragment;
	}

	public Fragment getFragmentByIdImplied()
	{
		return testfragment;
	}

	public TestFragmentSupport getTestFragment()
	{
		return mFragment;
	}
}
