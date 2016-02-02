package io.github.sporklibrary.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a layout.
 * Can be used on a Activity and ViewGroup classes.
 * When used on Activity, setContentView() is called.
 * When used on a ViewGroup, the layout is inflated into the ViewGroup. A FrameLayout is preferred for this.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindLayout
{
	/**
	 * @return the layout resource id from R.layout.*
	 */
	int value();
}
