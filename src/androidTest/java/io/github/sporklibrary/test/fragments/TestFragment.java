package io.github.sporklibrary.test.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.InjectView;
import io.github.sporklibrary.test.R;

public class TestFragment extends Fragment
{
	@InjectView(R.id.button)
	private Button mButton;

	@InjectView
	private Button button;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_test, container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
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
}
