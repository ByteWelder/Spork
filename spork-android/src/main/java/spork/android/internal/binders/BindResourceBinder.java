package spork.android.internal.binders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.FieldBinder;
import spork.android.BindResource;
import spork.android.extension.ContextResolver;
import spork.android.internal.utils.ResourceId;
import spork.internal.Reflection;

import static spork.BindFailedBuilder.bindFailedBuilder;

public class BindResourceBinder implements FieldBinder<BindResource> {
	private final ContextResolver contextResolver;

	public BindResourceBinder(ContextResolver contextResolver) {
		this.contextResolver = contextResolver;
	}

	@Override
	public void bind(Object object, BindResource annotation, Field field, Object... parameters) {
		Context context = contextResolver.resolveContext(object);

		if (context == null) {
			throw bindFailedBuilder(BindResource.class, "failed to retrieve Context from target object")
					.into(field)
					.build();
		}

		Object resource = getResource(context, annotation, field);

		if (resource == null) {
			throw bindFailedBuilder(BindResource.class, "resource not found")
					.into(field)
					.build();
		}

		Reflection.setFieldValue(BindResource.class, field, object, resource);
	}

	@Override
	public Class<BindResource> getAnnotationClass() {
		return BindResource.class;
	}

	// region Resource get methods

	@Nullable
	private Object getResource(Context context, BindResource annotation, Field field) {
		Class<?> fieldClass = field.getType();

		if (fieldClass == String.class) {
			return getString(context, annotation, field);
		} else if (fieldClass == Float.class || fieldClass == float.class) {
			return getDimension(context, annotation, field);
		} else if (fieldClass == Drawable.class) {
			return getDrawable(context, annotation, field);
		} else if (fieldClass == Integer.class || fieldClass == int.class) {
			return getInteger(context, annotation, field);
		} else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
			return getBoolean(context, annotation, field);
		} else {
			throw bindFailedBuilder(BindResource.class, "unsupported field type")
					.into(field)
					.build();
		}
	}

	@Nullable
	private String getString(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.NONE) {
			resourceId = context.getResources().getIdentifier(field.getName(), "string", context.getPackageName());
		}

		return context.getResources().getString(resourceId);
	}

	private float getDimension(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.NONE) {
			resourceId = context.getResources().getIdentifier(field.getName(), "dimen", context.getPackageName());
		}

		return context.getResources().getDimension(resourceId);
	}

	@SuppressWarnings("deprecation")
	@Nullable
	private Drawable getDrawable(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.NONE) {
			resourceId = context.getResources().getIdentifier(field.getName(), "drawable", context.getPackageName());
		}

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return context.getResources().getDrawable(resourceId);
		} else {
			return context.getDrawable(resourceId);
		}
	}

	private int getInteger(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.NONE) {
			resourceId = context.getResources().getIdentifier(field.getName(), "integer", context.getPackageName());
		}

		return context.getResources().getInteger(resourceId);
	}

	private boolean getBoolean(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.NONE) {
			resourceId = context.getResources().getIdentifier(field.getName(), "bool", context.getPackageName());
		}

		return context.getResources().getBoolean(resourceId);
	}

	// endregion
}