package io.github.sporklibrary.android.bindview;

import android.view.View;

public interface ViewProvider
{
	View getViewByIdSpecified();
	View getViewByImplied();
}
