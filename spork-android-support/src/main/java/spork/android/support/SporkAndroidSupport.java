package spork.android.support;

import spork.android.SporkAndroid;
import spork.android.SporkAndroidExtension;
import spork.android.support.internal.SupportContextResolver;
import spork.android.support.internal.SupportFragmentResolver;
import spork.android.support.internal.SupportViewResolver;

/**
 * Extension that adds view/context/fragment resolvers to spork-android.
 * This extension is automatically resolved by the spork-android module.
 */
public final class SporkAndroidSupport implements SporkAndroidExtension {

	@Override
	public void initialize(SporkAndroid sporkAndroid) {
		sporkAndroid.register(new SupportViewResolver());
		sporkAndroid.register(new SupportContextResolver());
		sporkAndroid.register(new SupportFragmentResolver());
	}
}
