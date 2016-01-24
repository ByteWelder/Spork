package io.github.sporklibrary.test.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class TestView extends FrameLayout
{
	@BindView(R.id.button)
	private Button mButton;

	@BindView
	private Button button;

	public TestView(Context context)
	{
		super(context);

		LayoutInflater.from(context).inflate(R.layout.view_test, this);

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
