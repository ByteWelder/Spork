package io.github.sporklibrary.annotations;

import io.github.sporklibrary.utils.ResourceId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a Fragment instance.
 * Can be used with classes derived from:
 * <ul>
 * <li>{@link android.app.Activity}</li>
 * <li>{@link android.app.Fragment}</li>
 * <li>{@link android.support.v4.app.Fragment}</li>
 * </ul>
 * The value specified is the Fragment id. When not specified, the name of the field will be used to resolve the id's name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindFragment {
    // TODO: implement support for finding fragments by tag

    /**
     * The value specified is the Fragment id. When not specified, the name of the field will be used to resolve the id's name.
     * For example: "@BindFragment Fragment my_fragment;" would bind to R.id.my_fragment
     *
     * @return Fragment resource id as defined in R.id.*
     */
    int value() default ResourceId.sDefaultValue;
}

