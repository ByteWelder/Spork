package io.github.sporklibrary.test.bindclick.domain;

import android.content.Context;
import android.widget.FrameLayout;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.test.R;

public class TestFaultyView extends FrameLayout
{
	public TestFaultyView(Context context)
	{
		super(context);

		Spork.bind(this);
	}

	@BindClick(R.id.click_binding_view_button)
	public void onClick()
	{
	}
}