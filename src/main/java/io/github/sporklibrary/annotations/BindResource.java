package io.github.sporklibrary.annotations;

import io.github.sporklibrary.utils.ResourceId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a resource object.
 * Can be used on a:
 *  - String (R.string.*)
 *  - Float, float (R.dimen.*)
 *  - Drawable (R.drawable.*)
 * Can be used within a Application/Activity/Fragment(regular and support)/View/RecyclerView.ViewHolder
 * including support library Activities and Fragments.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindResource
{
	/**
	 * The value specified is the id. When not specified, the name of the field will be used to resolve the id's name.
	 * For example: "@BindResource String my_string;" would bind to R.string.my_string
	 * @return resource id as defined in R.java
	 */
	int value() default ResourceId.sDefaultValue;
}
