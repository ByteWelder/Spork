package io.github.sporklibrary.test.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.test.R;

public class FragmentBindingFragment extends Fragment
{
	@BindFragment(R.id.fragment)
	private Fragment mFragment;

	@BindFragment
	private Fragment fragment;

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

	public Fragment getFragmentByIdSpecified()
	{
		return mFragment;
	}

	public Fragment getFragmentByIdImplied()
	{
		return fragment;
	}
}
