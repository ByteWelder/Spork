package io.github.sporklibrary.utils.support;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.utils.Classes;

/**
 * Provides support methods for V4 fragments
 */
public final class SupportFragments
{
	private static final @Nullable Class<?> sSupportFragmentClass = Classes.classForNameOrNull("android.support.v4.app.Fragment");

	private SupportFragments()
	{
	}
	public static boolean isFragmentClass(Class<?> classObject)
	{
		return sSupportFragmentClass != null && sSupportFragmentClass.isAssignableFrom(classObject);
	}
}
