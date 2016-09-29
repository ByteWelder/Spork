package io.github.sporklibrary.android.support.test.bindresource;

import android.graphics.drawable.Drawable;

import javax.annotation.Nullable;

public interface ResourceProvider
{
	@Nullable String getStringByIdSpecified();
	@Nullable String getStringByIdImplied();

	@Nullable Drawable getDrawableByIdSpecified();
	@Nullable Drawable getDrawableByIdImplied();

	float getDimensionByIdSpecified();
	float getDimensionByIdImplied();
}
