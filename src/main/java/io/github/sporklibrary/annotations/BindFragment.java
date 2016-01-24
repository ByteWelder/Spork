package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindFragment
{
	// TODO: implement support for finding fragments by tag

	/**
	 * @return Fragment resource id as defined in R.id.*
	 */
	int value() default 0;
}

