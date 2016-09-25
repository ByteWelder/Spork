package io.github.sporklibrary.android.interfaces;

import android.content.Context;

/**
 * Used by DefaultContextResolver to resolve Context for unsupported objects.
 */
public interface ContextProvider {
	Context getContext();
}
