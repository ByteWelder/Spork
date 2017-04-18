package spork.android.support.internal;

import android.support.v4.app.Fragment;
import android.view.View;

import javax.annotation.Nullable;

import spork.android.extension.ViewResolver;

/**
 * Resolves View instances from v4 support library types.
 */
public class SupportViewResolver implements ViewResolver {
	@Override
	@Nullable
	public View resolveView(Object object) {
		if (object instanceof Fragment) {
			return ((Fragment) object).getView();
		} else {
			return null;
		}
	}
}
