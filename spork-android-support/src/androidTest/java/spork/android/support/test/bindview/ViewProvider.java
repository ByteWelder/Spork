package spork.android.support.test.bindview;

import android.view.View;

public interface ViewProvider
{
	View getViewByIdSpecified();
	View getViewByImplied();
}
