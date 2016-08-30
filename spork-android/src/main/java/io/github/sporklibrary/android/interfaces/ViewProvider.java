package io.github.sporklibrary.android.interfaces;

import android.view.View;

/**
 * Used by DefaultViewResolver and DefaultContextResolver to resolve views for unsupported objects.
 */
public interface ViewProvider {
	View getView();
}
