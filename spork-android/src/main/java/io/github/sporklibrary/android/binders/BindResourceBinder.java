package io.github.sporklibrary.android.binders;

import android.content.Context;
import android.graphics.drawable.Drawable;

import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedFields;
import io.github.sporklibrary.android.resolvers.ContextResolverManager;
import io.github.sporklibrary.android.utils.ResourceId;

public class BindResourceBinder implements FieldBinder<BindResource> {
    @Override
    public Class<BindResource> getAnnotationClass() {
        return BindResource.class;
    }

    @Override
    public void bind(Object object, AnnotatedField<BindResource> annotatedField) {
        Context context = ContextResolverManager.shared().resolveContext(object);

        if (context == null) {
            throw new BindException(BindResource.class, object.getClass(), annotatedField.getField(), "failed to find Context: make sure your parent class is a View, Fragment or Activity");
        }

        Object field_object = getFieldObject(context, annotatedField);

        if (field_object == null) {
            throw new BindException(BindResource.class, object.getClass(), annotatedField.getField(), "resource not found");
        }

        AnnotatedFields.setValue(annotatedField, object, field_object);
    }

    @Nullable
    private Object getFieldObject(Context context, AnnotatedField<BindResource> annotatedField) {
        Class<?> field_class = annotatedField.getField().getType();

        if (field_class == String.class) {
            return getStringObject(context, annotatedField);
        } else if (field_class == Float.class || field_class == float.class) {
            return getDimensionFieldObject(context, annotatedField);
        } else if (field_class == Drawable.class) {
            return getDrawableFieldObject(context, annotatedField);
        } else if (field_class == Integer.class || field_class == int.class) {
            return getIntegerFieldObject(context, annotatedField);
        } else if (field_class == Boolean.class || field_class == boolean.class) {
            return getBooleanFieldObject(context, annotatedField);
        } else {
            throw new BindException(BindResource.class, annotatedField.getField().getDeclaringClass(), annotatedField.getField(), "unsupported field type");
        }
    }

    @Nullable
    private String getStringObject(Context context, AnnotatedField<BindResource> annotatedField) {
        int resource_id = annotatedField.getAnnotation().value();

        if (resource_id == ResourceId.sDefaultValue) {
            resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "string", context.getPackageName());
        }

        return context.getResources().getString(resource_id);
    }

    private float getDimensionFieldObject(Context context, AnnotatedField<BindResource> annotatedField) {
        int resource_id = annotatedField.getAnnotation().value();

        if (resource_id == ResourceId.sDefaultValue) {
            resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "dimen", context.getPackageName());
        }

        return context.getResources().getDimension(resource_id);
    }

    private int getIntegerFieldObject(Context context, AnnotatedField<BindResource> annotatedField) {
        int resource_id = annotatedField.getAnnotation().value();

        if (resource_id == ResourceId.sDefaultValue) {
            resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "integer", context.getPackageName());
        }

        return context.getResources().getInteger(resource_id);
    }

    private boolean getBooleanFieldObject(Context context, AnnotatedField<BindResource> annotatedField) {
        int resource_id = annotatedField.getAnnotation().value();

        if (resource_id == ResourceId.sDefaultValue) {
            resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "bool", context.getPackageName());
        }

        return context.getResources().getBoolean(resource_id);
    }

    @Nullable
    private Drawable getDrawableFieldObject(Context context, AnnotatedField<BindResource> annotatedField) {
        int resource_id = annotatedField.getAnnotation().value();

        if (resource_id == ResourceId.sDefaultValue) {
            resource_id = context.getResources().getIdentifier(annotatedField.getField().getName(), "drawable", context.getPackageName());
        }

        return context.getResources().getDrawable(resource_id);
    }
}