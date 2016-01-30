package io.github.sporklibrary.test.bindview;

import android.view.View;

public interface ViewProvider
{
	View getViewByIdSpecified();
	View getViewByImplied();
}
