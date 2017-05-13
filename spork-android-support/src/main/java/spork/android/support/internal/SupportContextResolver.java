package spork.android.support.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import spork.android.extension.ContextResolver;

/**
 * Resolves Context from v4 support library types.
 */
public final class SupportContextResolver implements ContextResolver {

	@Override
	public @Nullable Context resolveContext(Object object) {
		if (object instanceof Fragment) {
			return ((Fragment) object).getActivity();
		} else {
			return null;
		}
	}
}
