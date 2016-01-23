package io.github.sporklibrary;

import javax.annotation.Nullable;

public class Spork
{
	private static @Nullable Injector mInjector;

	/**
	 * Inject a single object with all relevant instances.
	 * @param object the object to inject into
	 */
	public static void inject(Object object)
	{
		getInjector().inject(object);
	}

	public static Injector getInjector()
	{
		// Only create an injector if the code is actually used
		if (mInjector == null)
		{
			mInjector = new Injector();
		}

		return mInjector;
	}
}
