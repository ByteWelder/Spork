package io.github.sporklibrary.test;

import android.widget.Button;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.InjectView;

public class FaultyInjection
{
	@InjectView(R.id.button)
	private Button mButton;

	public FaultyInjection()
	{
		Spork.inject(this);
	}
}
