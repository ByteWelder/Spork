package io.github.sporklibrary.test.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class BadBindingView extends View
{
	@BindView(R.id.button)
	private Button mButton;

	public BadBindingView(Context context)
	{
		super(context);

		Spork.bind(this);
	}
}
