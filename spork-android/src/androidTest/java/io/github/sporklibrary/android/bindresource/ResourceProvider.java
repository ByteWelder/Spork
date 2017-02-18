package io.github.sporklibrary.android.bindresource;

import android.graphics.drawable.Drawable;

import javax.annotation.Nullable;

public interface ResourceProvider
{
	@Nullable String getStringByIdSpecified();
	@Nullable String getStringByIdImplied();

	@Nullable Drawable getDrawableByIdSpecified();
	@Nullable Drawable getDrawableByIdImplied();

	int getIntByIdImplied();
	@Nullable Integer getIntegerByIdSpecified();

	float getDimensionByIdSpecified();
	float getDimensionByIdImplied();

	boolean getBooleanByIdImplied();
	@Nullable Boolean getBooleanByIdSpecified();
}
