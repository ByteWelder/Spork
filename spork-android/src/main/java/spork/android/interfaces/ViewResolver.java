package spork.android.interfaces;

import android.view.View;

import spork.annotations.Nullable;

public interface ViewResolver {
	@Nullable View resolveView(Object object);
}
