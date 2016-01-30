package io.github.sporklibrary.test.bindview.domain;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

public class CorruptTestView extends View
{
	@BindView(R.id.testview_button)
	private Button mButton;

	public CorruptTestView(Context context)
	{
		super(context);

		Spork.bind(this);
	}
}
