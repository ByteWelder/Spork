package io.github.sporklibrary.test.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.InjectView;
import io.github.sporklibrary.test.R;

public class TestView extends FrameLayout
{
	@InjectView(R.id.button)
	private Button mButton;

	@InjectView
	private Button button;

	public TestView(Context context)
	{
		super(context);

		LayoutInflater.from(context).inflate(R.layout.view_test, this);

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
