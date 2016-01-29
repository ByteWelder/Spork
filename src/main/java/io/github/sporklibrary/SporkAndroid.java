package io.github.sporklibrary;

import io.github.sporklibrary.binders.BindClickBinder;
import io.github.sporklibrary.binders.BindFragmentBinder;
import io.github.sporklibrary.binders.BindViewBinder;

public class SporkAndroid
{
	private static boolean sInitialized = false;

	/**
	 * Needs to be called at least once to register all binders for Android.
	 * Repeated calls are accepted and won't degrade performance in any noticable way.
	 */
	public static void initialize()
	{
		if (sInitialized)
		{
			return;
		}

		// Register binders
		Spork.getBinderManager().register(new BindViewBinder());
		Spork.getBinderManager().register(new BindFragmentBinder());
		Spork.getBinderManager().register(new BindClickBinder());

		sInitialized = true;
	}
}
