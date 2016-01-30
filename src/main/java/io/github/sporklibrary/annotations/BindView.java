package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a View instance.
 * Can be used on a View field in a Fragment/Activity/View.
 * The bound View field can be a View or any class derived from it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindView
{
	/**
	 * The value specified is the View id. When not specified, the name of the field will be used to resolve the id's name.
	 * For example: "@BindView View my_view;" would bind to R.id.my_view
	 * @return View resource id as defined in R.id.*
	 */
	int value() default 0;
}
