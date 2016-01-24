package io.github.sporklibrary.exceptions;

public class NotSupportedException extends RuntimeException
{
	public NotSupportedException(String message)
	{
		super(message);
	}

	public NotSupportedException(String message, Exception e)
	{
		super(message, e);
	}
}
