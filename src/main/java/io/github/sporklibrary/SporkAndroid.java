package io.github.sporklibrary;

import io.github.sporklibrary.binders.ClickMethodBinder;
import io.github.sporklibrary.binders.FragmentFieldBinder;
import io.github.sporklibrary.binders.ViewFieldBinder;

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
		Spork.getBinderManager().register(new ViewFieldBinder());
		Spork.getBinderManager().register(new FragmentFieldBinder());
		Spork.getBinderManager().register(new ClickMethodBinder());

		sInitialized = true;
	}
}
