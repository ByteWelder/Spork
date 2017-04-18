package spork.android;

import android.content.Context;

/**
 * Used by {@link spork.android.internal.DefaultContextResolver} to resolve Context for unsupported objects.
 * This enables Android injection on custom Android objects.
 */
public interface ContextProvider {
	Context getContext();
}
