package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a View's OnClickListener result.
 * Can be used with a View/Fragment/Activity method.
 * The value specified is the View id. When not specified, the name of the method will be used to resolve the id's name.
 * For example: "@BindClick void login() {}" would bind to R.id.login
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindClick
{
	/**
	 * @return View resource id as defined in R.id.*
	 */
	int value() default 0;
}

