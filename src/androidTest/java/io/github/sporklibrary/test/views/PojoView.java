package io.github.sporklibrary.test.views;

import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.test.R;

/**
 * An Object that is not derived from View
 */
public class PojoView
{
	@BindView(R.id.button)
	private Button mButton;

	public PojoView()
	{
		Spork.bind(this);
	}
}
