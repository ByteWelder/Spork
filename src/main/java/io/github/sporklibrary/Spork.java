package io.github.sporklibrary;

import io.github.sporklibrary.component.ComponentFieldInjector;

import javax.annotation.Nullable;

public class Spork
{
	private static @Nullable InjectionManager sInjectionManager;

	/**
	 * Inject a single object with all relevant instances.
	 * @param object the object to inject into
	 */
	public static void inject(Object object)
	{
		getInjectionManager().inject(object);
	}

	public static InjectionManager getInjectionManager()
	{
		// Only create an injector if the code is actually used
		if (sInjectionManager == null)
		{
			sInjectionManager = new InjectionManager();
			sInjectionManager.register(new ComponentFieldInjector());
		}

		return sInjectionManager;
	}
}
