package io.github.sporklibrary.test;

import android.app.Application;
import io.github.sporklibrary.SporkAndroid;

public class TestRunner extends android.support.test.runner.AndroidJUnitRunner
{
	@Override
	public void callApplicationOnCreate(Application app)
	{
		SporkAndroid.initialize();

		super.callApplicationOnCreate(app);
	}
}