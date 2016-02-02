package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used with a Component class to set its scope.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ComponentScope
{
	enum Scope
	{
		DEFAULT,    // New instance per injection
		SINGLETON   // Only 1 instance is ever created (and it will never be garbage-collected while the application is running)
	}

	Scope value() default Scope.DEFAULT;
}
