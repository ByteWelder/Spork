package io.github.sporklibrary.test.contextprovider.domain;

import android.app.Activity;
import android.os.Bundle;
import android.support.test.filters.SmallTest;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;

@SmallTest
public class TestActivity extends Activity
{
	@BindComponent
	private TestComponent mTestComponent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Spork.bind(this);
	}

	public TestComponent getTestComponent()
	{
		return mTestComponent;
	}
}

