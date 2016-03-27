package io.github.sporklibrary.binders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import io.github.sporklibrary.annotations.BindResource;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.utils.ContextResolver;
import io.github.sporklibrary.utils.ResourceId;

public class BindResourceBinder implements FieldBinder<BindResource>
{
	@Override
	public Class<BindResource> getAnnotationClass()
	{
		return BindResource.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindResource> annotatedField)
	{
		Context context = ContextResolver.getContext(object);

		if (context == null)
		{
			throw new BindException(BindResource.class, object.getClass(), annotatedField.getField(), "failed to find Context: make sure your parent class is a View, Fragment or Activity");
		}

		Object field_object = getFieldObject(context, annotatedField);

		if (field_object == null)
		{
			throw new BindException(BindResource.class, object.getClass(), annotatedField.getField(), "resource not found");
		}

		AnnotatedFields.setValue(annotatedField, object, field_object);
	}

	private @Nullable Object getFieldObject(Context context, AnnotatedField<BindResource> annotatedField)
	{
		Class<?> field_class = annotatedField.getField().getType();

		if (field_class == String.class)
		{
			return getStringObject(context, annotatedField);
		}
		else if (field_class == Float.class || field_class == float.class)
		{
			return getDimensionFieldObject(context, annotatedField);
		}
		else if (field_class == Drawable.class)
		{
			return getDrawableFieldObject(context, annotatedField);
		}
		else
		{
			throw new BindException(BindResource.class, annotatedField.getField().getDeclaringClass(), annotatedField.getField(), "unsupported field type");
		}
	}

	private @Nullable String getStringObject(Context context, AnnotatedField<BindResource> annotatedField)
	{
		int resource_id = annotatedField.getAnnotation().value();

		if (resource_id == ResourceId.sDefaultValue)
		{
			resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "string", context.getPackageName());
		}

		return context.getResources().getString(resource_id);
	}

	private float getDimensionFieldObject(Context context, AnnotatedField<BindResource> annotatedField)
	{
		int resource_id = annotatedField.getAnnotation().value();

		if (resource_id == ResourceId.sDefaultValue)
		{
			resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "dimen", context.getPackageName());
		}

		return context.getResources().getDimension(resource_id);
	}

	private @Nullable Drawable getDrawableFieldObject(Context context, AnnotatedField<BindResource> annotatedField)
	{
		int resource_id = annotatedField.getAnnotation().value();

		if (resource_id == ResourceId.sDefaultValue)
		{
			resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "drawable", context.getPackageName());
		}

		return context.getResources().getDrawable(resource_id);
	}
}