package spork.android.extension;

import android.view.View;

import javax.annotation.Nullable;

/**
 * Resolve a {@link View} for the given {@link Object}.
 *
 * For example: If the object is a Fragment, the returned view will be the Fragment's root View.
 */
public interface ViewResolver {
	/**
	 * Try to resolve a View from the given object
	 * @param object the instance to resolve a View from
	 * @return the View or null when none is found
	 * @throws Exception when something unexpected happens
	 */
	@Nullable
	View resolveView(Object object) throws Exception;
}
