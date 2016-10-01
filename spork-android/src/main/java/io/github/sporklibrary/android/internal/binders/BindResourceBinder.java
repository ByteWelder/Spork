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
	private final ContextResolver contextResolver;

	public BindResourceBinder(ContextResolver contextResolver) {
		this.contextResolver = contextResolver;
	}

	@Override
	public void bind(Object object, BindResource annotation, Field field, Object[] modules) {
		Context context = contextResolver.resolveContext(object);

		if (context == null) {
			throw new BindException(BindResource.class, object.getClass(), field, "failed to find Context: make sure your parent class is a View, Fragment or Activity");
		}

		Object fieldObject = getFieldObject(context, annotation, field);

		if (fieldObject == null) {
			throw new BindException(BindResource.class, object.getClass(), field, "resource not found");
		}

		Reflection.setFieldValue(annotation, field, object, fieldObject);
	}

	@Override
	public Class<BindResource> getAnnotationClass() {
		return BindResource.class;
	}

	@Nullable
	private Object getFieldObject(Context context, BindResource annotation, Field field) {
		Class<?> fieldClass = field.getType();

		if (fieldClass == String.class) {
			return getStringObject(context, annotation, field);
		} else if (fieldClass == Float.class || fieldClass == float.class) {
			return getDimensionFieldObject(context, annotation, field);
		} else if (fieldClass == Drawable.class) {
			return getDrawableFieldObject(context, annotation, field);
		} else {
			throw new BindException(BindResource.class, field.getDeclaringClass(), field, "unsupported field type");
		}
	}

	@Nullable
	private String getStringObject(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.sDefaultValue) {
			resourceId = context.getResources().getIdentifier(field.getName(), "string", context.getPackageName());
		}

		return context.getResources().getString(resourceId);
	}

	private float getDimensionFieldObject(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.sDefaultValue) {
			resourceId = context.getResources().getIdentifier(field.getName(), "dimen", context.getPackageName());
		}

		return context.getResources().getDimension(resourceId);
	}

	@SuppressWarnings("deprecation")
	@Nullable
	private Drawable getDrawableFieldObject(Context context, BindResource annotation, Field field) {
		int resourceId = annotation.value();

		if (resourceId == ResourceId.sDefaultValue) {
			resourceId = context.getResources().getIdentifier(field.getName(), "drawable", context.getPackageName());
		}

		if (Build.VERSION.SDK_INT < 21) {
			return context.getResources().getDrawable(resourceId);
		} else {
			return context.getDrawable(resourceId);
		}
	}
}