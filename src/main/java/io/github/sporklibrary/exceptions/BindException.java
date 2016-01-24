package io.github.sporklibrary.exceptions;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public class BindException extends RuntimeException
{
	public BindException(Class<? extends Annotation> annotation, Class<?> parent, String reason, @Nullable  Exception cause)
	{
		super(annotation.getClass().getSimpleName() + " failed for " + parent.getClass().getName()  + ": " + reason, cause);
	}

	public BindException(Class<? extends Annotation> annotation, Class<?> parent, String reason)
	{
		this(annotation, parent, reason, null);
	}
}
