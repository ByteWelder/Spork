package io.github.sporklibrary.test.exceptions;

import io.github.sporklibrary.exceptions.NotSupportedException;

import org.junit.Test;

public class NotSupportedExceptionTest {

    @Test(expected = NotSupportedException.class)
    public void testThrow1() {
        throw new NotSupportedException("test");
    }

    @Test(expected = NotSupportedException.class)
    public void testThrow2() {
        throw new NotSupportedException("test", new Exception());
    }
}
