package io.github.sporklibrary.android.support;

import io.github.sporklibrary.android.SporkAndroid;
import io.github.sporklibrary.android.SporkAndroidExtension;
import io.github.sporklibrary.android.support.internal.SupportContextResolver;
import io.github.sporklibrary.android.support.internal.SupportFragmentResolver;
import io.github.sporklibrary.android.support.internal.SupportViewResolver;

public final class SporkAndroidSupport implements SporkAndroidExtension {

	@Override
	public void initialize(SporkAndroid sporkAndroid) {
		sporkAndroid.getViewResolverRegistry().register(new SupportViewResolver());
		sporkAndroid.getContextResolverRegistry().register(new SupportContextResolver());
		sporkAndroid.getFragmentResolverRegistry().register(new SupportFragmentResolver());
	}
}
