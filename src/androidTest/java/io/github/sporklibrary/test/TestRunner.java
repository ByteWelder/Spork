package io.github.sporklibrary.test;

import android.app.Application;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.ViewFieldInjector;

public class TestRunner extends android.support.test.runner.AndroidJUnitRunner
{
	@Override
	public void callApplicationOnCreate(Application app)
	{
		// Register ViewInjectionProvider
		Spork.getInjectionManager().register(new ViewFieldInjector());

		super.callApplicationOnCreate(app);
	}
}