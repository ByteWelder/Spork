package io.github.sporklibrary.android.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a layout.
 * Can be used with classes derived from:
 * <ul>
 * <li>{@link android.view.ViewGroup}</li>
 * <li>{@link android.app.Activity}</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindLayout {
    /**
     * @return the layout resource id from R.layout.*
     */
    int value();
}
