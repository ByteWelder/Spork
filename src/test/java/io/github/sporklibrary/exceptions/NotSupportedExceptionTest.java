package io.github.sporklibrary.exceptions;

import org.junit.Test;

public class NotSupportedExceptionTest
{
	@Test(expected = NotSupportedException.class)
	public void testThrow1()
	{
		throw new NotSupportedException("test");
	}

	@Test(expected = NotSupportedException.class)
	public void testThrow2()
	{
		throw new NotSupportedException("test", new Exception());
	}
}
