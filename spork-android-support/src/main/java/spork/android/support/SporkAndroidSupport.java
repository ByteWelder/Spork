package spork.android.support;

import spork.android.SporkAndroid;
import spork.android.SporkAndroidExtension;
import spork.android.support.internal.SupportContextResolver;
import spork.android.support.internal.SupportFragmentResolver;
import spork.android.support.internal.SupportViewResolver;

public final class SporkAndroidSupport implements SporkAndroidExtension {

	@Override
	public void initialize(SporkAndroid sporkAndroid) {
		sporkAndroid.getViewResolverRegistry().register(new SupportViewResolver());
		sporkAndroid.getContextResolverRegistry().register(new SupportContextResolver());
		sporkAndroid.getFragmentResolverRegistry().register(new SupportFragmentResolver());
	}
}
