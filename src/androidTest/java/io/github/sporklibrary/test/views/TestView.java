package io.github.sporklibrary.test.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class TestView extends FrameLayout
{
	@BindView(R.id.testview_button)
	private Button mButton;

	@BindView
	private Button testview_button;

	private int mButtonClickCount = 0;

	public TestView(Context context)
	{
		super(context);

		init(context);
	}

	public TestView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		init(context);
	}

	public TestView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.view_test, this);

		Spork.bind(this);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public Button getButtonByIdSpecified()
	{
		return mButton;
	}

	public Button getButtonByIdImplied()
	{
		return testview_button;
	}

	@BindClick(R.id.testview_button)
	private void onClickButton()
	{
		mButtonClickCount++;
	}

	public int getButtonClickCount()
	{
		return mButtonClickCount;
	}
}
