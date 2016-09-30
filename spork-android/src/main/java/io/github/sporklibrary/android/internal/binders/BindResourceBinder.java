package io.github.sporklibrary.android.internal.binders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.android.interfaces.ContextResolver;
import io.github.sporklibrary.android.internal.utils.ResourceId;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.internal.Reflection;

public class BindResourceBinder implements FieldBinder<BindResource> {
	private ContextResolver contextResolver;

	public BindResourceBinder(ContextResolver contextResolver) {
		this.contextResolver = contextResolver;
	}

	@Override
	public void bind(Object object, BindResource annotation, Field field, @Nullable Object[] modules) {
		Context context = contextResolver.resolveContext(object);

		if (context == null) {
			throw new BindException(BindResource.class, object.getClass(), field, "failed to find Context: make sure your parent class is a View, Fragment or Activity");
		}

		Object field_object = getFieldObject(context, annotation, field);

		if (field_object == null) {
			throw new BindException(BindResource.class, object.getClass(), field, "resource not found");
		}

		Reflection.setFieldValue(annotation, field, object, field_object);
	}

	@Override
	public Class<BindResource> getAnnotationClass() {
		return BindResource.class;
	}

	@Nullable
	private Object getFieldObject(Context context, BindResource annotation, Field field) {
		Class<?> field_class = field.getType();

		if (field_class == String.class) {
			return getStringObject(context, annotation, field);
		} else if (field_class == Float.class || field_class == float.class) {
			return getDimensionFieldObject(context, annotation, field);
		} else if (field_class == Drawable.class) {
			return getDrawableFieldObject(context, annotation, field);
		} else {
			throw new BindException(BindResource.class, field.getDeclaringClass(), field, "unsupported field type");
		}
	}

	@Nullable
	private String getStringObject(Context context, BindResource annotation, Field field) {
		int resource_id = annotation.value();

		if (resource_id == ResourceId.sDefaultValue) {
			resource_id = context.getResources().getIdentifier(field.getName(), "string", context.getPackageName());
		}

		return context.getResources().getString(resource_id);
	}

	private float getDimensionFieldObject(Context context, BindResource annotation, Field field) {
		int resource_id = annotation.value();

		if (resource_id == ResourceId.sDefaultValue) {
			resource_id = context.getResources().getIdentifier(field.getName(), "dimen", context.getPackageName());
		}

		return context.getResources().getDimension(resource_id);
	}

	@SuppressWarnings("deprecation")
	@Nullable
	private Drawable getDrawableFieldObject(Context context, BindResource annotation, Field field) {
		int resource_id = annotation.value();

		if (resource_id == ResourceId.sDefaultValue) {
			resource_id = context.getResources().getIdentifier(field.getName(), "drawable", context.getPackageName());
		}

		if (Build.VERSION.SDK_INT < 21) {
			return context.getResources().getDrawable(resource_id);
		} else {
			return context.getDrawable(resource_id);
		}
	}
}