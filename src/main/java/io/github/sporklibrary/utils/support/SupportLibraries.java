package io.github.sporklibrary.utils.support;

import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.utils.Classes;

public final class SupportLibraries
{
	private static final @Nullable boolean sHasSupportV4;

	private static final @Nullable boolean sHasAppCompatV7;

	static
	{
		sHasSupportV4 = Classes.classForNameOrNull("android.support.v4.BuildConfig") != null;
		sHasAppCompatV7 = Classes.classForNameOrNull("android.support.v7.appcompat.BuildConfig") != null;
	}

	public static boolean hasSupportV4()
	{
		return sHasSupportV4;
	}

	public static boolean hasAppCompatV7()
	{
		return sHasAppCompatV7;
	}
}
