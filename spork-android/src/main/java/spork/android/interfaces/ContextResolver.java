package spork.android.interfaces;

import android.content.Context;

import spork.annotations.Nullable;

/**
 * Resolves a Context for a given object or returns null when it cannot be resolved.
 */
public interface ContextResolver {
	@Nullable Context resolveContext(Object object);
}
