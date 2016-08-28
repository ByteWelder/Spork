package io.github.sporklibrary.android.support.bindview;

import android.view.View;

public interface ViewProvider
{
	View getViewByIdSpecified();
	View getViewByImplied();
}
