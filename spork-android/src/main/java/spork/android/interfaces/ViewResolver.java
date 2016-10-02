package spork.android.interfaces;

import android.view.View;

import javax.annotation.Nullable;

public interface ViewResolver {
	@Nullable
	View resolveView(Object object);
}
