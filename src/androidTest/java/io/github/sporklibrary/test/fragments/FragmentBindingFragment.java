package io.github.sporklibrary.test.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindFragment;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.views.TestView;

public class FragmentBindingFragment extends Fragment
{
	@BindFragment(R.id.fragment)
	private Fragment mFragment;

	@BindFragment
	private Fragment fragment;

	@BindView(R.id.fbf_testview)
	private TestView fbf_testview;

	private boolean mClickedTestView = false;

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

	@BindClick(R.id.fbf_testview)
	private void onClickTestView()
	{
		mClickedTestView = true;
	}

	public Fragment getFragmentByIdSpecified()
	{
		return mFragment;
	}

	public Fragment getFragmentByIdImplied()
	{
		return fragment;
	}

	public boolean hasClickedTestView()
	{
		return mClickedTestView;
	}

	public TestView getTestView()
	{
		return fbf_testview;
	}
}
