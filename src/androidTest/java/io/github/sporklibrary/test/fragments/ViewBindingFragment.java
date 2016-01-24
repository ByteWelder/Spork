package io.github.sporklibrary.test.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class ViewBindingFragment extends Fragment
{
	@BindView(R.id.button)
	private Button mButton;

	@BindView
	private Button button;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_view_binding, container);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		Spork.bind(this);
	}

	public Button getButtonByIdSpecified()
	{
		return mButton;
	}

	public Button getButtonByIdImplied()
	{
		return button;
	}
}
