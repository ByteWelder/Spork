package io.github.sporklibrary.test.bindfragment.domain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragmentSupport extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		return new View(getActivity());
	}
}
