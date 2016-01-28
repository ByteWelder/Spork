package io.github.sporklibrary.test.views;

import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

/**
 * An Object that is not derived from View
 */
public class BindViewPojo
{
	@BindView(R.id.testview_button)
	private Button mButton;

	public BindViewPojo()
	{
		Spork.bind(this);
	}
}
