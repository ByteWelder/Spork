package io.github.sporklibrary;

import io.github.sporklibrary.binders.component.ComponentFieldBinder;

import io.github.sporklibrary.annotations.Nullable;

public class Spork
{
	private static @Nullable BinderManager sBinderManager;

	/**
	 * Bind a single object with all relevant instances.
	 * @param object the object to bind into
	 */
	public static void bind(Object object)
	{
		getBinderManager().bind(object);
	}

	public static BinderManager getBinderManager()
	{
		// Only create an binder if the code is actually used
		if (sBinderManager == null)
		{
			sBinderManager = new BinderManager();
			sBinderManager.register(new ComponentFieldBinder());
		}

		return sBinderManager;
	}
}
