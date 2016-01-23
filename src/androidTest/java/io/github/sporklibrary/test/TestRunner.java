package io.github.sporklibrary.test;

import android.app.Application;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.ViewInjectionProvider;

public class TestRunner extends android.support.test.runner.AndroidJUnitRunner
{
	@Override
	public void callApplicationOnCreate(Application app)
	{
		// Register ViewInjectionProvider
		Spork.getInjector().addInjectionProvider(new ViewInjectionProvider());

		super.callApplicationOnCreate(app);
	}
}