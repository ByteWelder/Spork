package spork.android.internal.binders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.android.BindResource;
import spork.android.ContextProvider;
import spork.android.extension.ContextResolver;
import spork.android.internal.utils.ResourceId;
import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.extension.FieldBinder;

public class BindResourceBinder implements FieldBinder<BindResource> {
	private final ContextResolver contextResolver;

	public BindResourceBinder(ContextResolver contextResolver) {
		this.contextResolver = contextResolver;
	}

	@Override
	public void bind(Object object, BindResource annotation, Field field, Object... parameters) throws BindFailed {
		Context context;

		try {
			context = contextResolver.resolveContext(object);
		} catch (Exception caught) {
			String message = new ExceptionMessageBuilder("Failed to resolve resource for field")
					.annotation(BindResource.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		}

		if (context == null) {
			String message = new ExceptionMessageBuilder("Failed to retrieve Context from target object")
					.annotation(BindResource.class)
					.suggest("make sure you're binding a supported Android object or that it implements " + ContextProvider.class.getName())
					.suggest("if you're binding a support library class, ensure you added the 'android-support' dependency")
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
		}

		Object resource = getResource(context, annotation, field);

		if (resource == null) {
			String message = new ExceptionMessageBuilder("Resource not found")
					.annotation(BindResource.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
		}

		try {
			field.setAccessible(true);
			field.set(object, resource);
		} catch (IllegalAccessException caught) {
			String message = new ExceptionMessageBuilder("Failed to access " + field.toString())
					.suggest("There might be a concurrency problem or you are trying to access a final static Field.")
					.annotation(BindResource.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		} finally {
			field.setAccessible(false);
		}
	}

	@Override
	public Class<BindResource> getAnnotationClass() {
		return BindResource.class;
	}

	// region Resource get methods

	@Nullable
	private Object getResource(Context context, BindResource annotation, Field field) throws BindFailed {
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
			String message = new ExceptionMessageBuilder("Unsupported field type: " + field.getType().getName())
					.annotation(BindResource.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
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