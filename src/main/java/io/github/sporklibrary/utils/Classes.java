package io.github.sporklibrary.utils;

public final class Classes
{
	private Classes()
	{
	}

	public static Class<?> classForNameOrNull(String name)
	{
		try
		{
			return Class.forName(name);
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
	}
}
