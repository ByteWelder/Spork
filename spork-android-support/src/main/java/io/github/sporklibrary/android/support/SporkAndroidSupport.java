package io.github.sporklibrary.android.support;

import io.github.sporklibrary.android.resolvers.ContextResolverManager;
import io.github.sporklibrary.android.resolvers.FragmentResolverManager;
import io.github.sporklibrary.android.resolvers.ViewResolverManager;
import io.github.sporklibrary.android.support.resolvers.SupportContextResolver;
import io.github.sporklibrary.android.support.resolvers.SupportFragmentResolver;
import io.github.sporklibrary.android.support.resolvers.SupportViewResolver;

public final class SporkAndroidSupport {
	private SporkAndroidSupport() {
	}

	public static void initialize() {
		ViewResolverManager.shared().addViewResolver(new SupportViewResolver());
		ContextResolverManager.shared().addContextResolver(new SupportContextResolver());
		FragmentResolverManager.shared().addFragmentResolver(new SupportFragmentResolver());
	}
}
