package io.github.sporklibrary.interfaces;

import android.view.View;

import io.github.sporklibrary.annotations.Nullable;

public interface ViewResolver {
	@Nullable View resolveView(Object object);
}
