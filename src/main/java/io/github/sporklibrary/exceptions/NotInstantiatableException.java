package io.github.sporklibrary.exceptions;

public class NotInstantiatableException extends NotSupportedException
{
	public NotInstantiatableException(Class<?> type)
	{
		super(type.getName() + "cannot be instantiated");
	}
}
