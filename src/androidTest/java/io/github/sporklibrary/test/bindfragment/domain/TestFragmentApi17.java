package io.github.sporklibrary.test.bindfragment.domain;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindfragment.FragmentProvider;

public class TestFragmentApi17 extends Fragment implements FragmentProvider
{
	@BindFragment(R.id.testfragmentapi17)
	private Fragment mFragment;

	@BindFragment
	private Fragment testfragmentapi17;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_fragment_binding, container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		Spork.bind(this);
	}

	@Override
	public Fragment getFragmentByIdSpecified()
	{
		return mFragment;
	}

	@Override
	public Fragment getFragmentByIdImplied()
	{
		return testfragmentapi17;
	}
}
