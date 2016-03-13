package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BindComponent
{
	final class Default {}

	/**
	 * Specifies an override for the implementation to bind.
	 * When Default.class is used, the Field's class will be used.
	 * @return the implementation class
	 */
	Class<?> value() default Default.class;
}
