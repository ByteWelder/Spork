package net.kenvanhoeylandt.spork.injectors;

import net.kenvanhoeylandt.spork.Injector;

public class CombinedInjector implements Injector
{
	private final DefaultInjector mDefaultInjector = new DefaultInjector();

	@Override
	public void inject(Object object)
	{
		mDefaultInjector.inject(object);
	}
}
