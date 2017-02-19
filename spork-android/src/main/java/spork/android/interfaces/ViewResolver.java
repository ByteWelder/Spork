package spork.android.interfaces;

import android.view.View;

import javax.annotation.Nullable;

/**
 * Resolve a {@link View} for the given {@link Object}.
 *
 * For example: If the object is a Fragment, the returned view will be the Fragment's root View.
 */
public interface ViewResolver {
	@Nullable
	View resolveView(Object object);
}
