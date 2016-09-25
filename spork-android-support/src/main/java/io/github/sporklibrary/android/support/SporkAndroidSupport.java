package io.github.sporklibrary.android.support;

import io.github.sporklibrary.android.SporkAndroid;
import io.github.sporklibrary.android.support.internal.SupportContextResolver;
import io.github.sporklibrary.android.support.internal.SupportFragmentResolver;
import io.github.sporklibrary.android.support.internal.SupportViewResolver;

public final class SporkAndroidSupport {
	private SporkAndroidSupport() {
	}

	/**
	 * This method is automatically called by the spork-android code. Do not call this manually.
	 */
	public static void initialize() {
		SporkAndroid.getViewResolverRegistry().register(new SupportViewResolver());
		SporkAndroid.getContextResolverRegistry().register(new SupportContextResolver());
		SporkAndroid.getFragmentResolverRegistry().register(new SupportFragmentResolver());
	}
}
