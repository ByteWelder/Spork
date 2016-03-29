package io.github.sporklibrary.utils.support;

public final class SupportLibraries
{
	private SupportLibraries()
	{
	}

	private static boolean sHasSupportV4;

	private static boolean sHasAppCompatV7;

	private static boolean sHasRecyclerViewV7;

	static
	{
		try
		{
			android.support.v4.BuildConfig.class.getName();
			sHasSupportV4 = true;
		}
		catch (NoClassDefFoundError caught)
		{
			sHasSupportV4 = false;
		}

		try
		{
			android.support.v7.appcompat.BuildConfig.class.getName();
			sHasAppCompatV7 = true;
		}
		catch (NoClassDefFoundError caught)
		{
			sHasAppCompatV7 = false;
		}

		try
		{
			android.support.v7.widget.RecyclerView.class.getName();
			sHasRecyclerViewV7 = true;
		}
		catch (NoClassDefFoundError caught)
		{
			sHasRecyclerViewV7 = false;
		}
	}

	public static boolean hasSupportV4()
	{
		return sHasSupportV4;
	}

	public static boolean hasAppCompatV7()
	{
		return sHasAppCompatV7;
	}

	public static boolean hasRecyclerViewV7()
	{
		return sHasRecyclerViewV7;
	}
}
