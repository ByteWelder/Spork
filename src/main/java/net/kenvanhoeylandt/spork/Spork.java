package net.kenvanhoeylandt.spork;

import net.kenvanhoeylandt.spork.injectors.CombinedInjector;

import javax.annotation.Nullable;

public class Spork
{
	private static @Nullable CombinedInjector mCombinedInjector;

	public static void inject(Object object)
	{
		if (mCombinedInjector == null)
		{
			mCombinedInjector = new CombinedInjector();
		}

		mCombinedInjector.inject(object);
	}
}
