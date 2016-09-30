package io.github.sporklibrary.android.internal.binders;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.interfaces.FragmentResolver;
import io.github.sporklibrary.android.internal.utils.ResourceId;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.internal.Reflection;

public class BindFragmentBinder implements FieldBinder<BindFragment> {
	private final FragmentResolver fragmentResolver;

	public BindFragmentBinder(FragmentResolver fragmentResolver) {
		this.fragmentResolver = fragmentResolver;
	}

	@Override
	public void bind(Object object, BindFragment annotation, Field field, @Nullable Object[] modules) {
		int id = annotation.value();
		@Nullable Object fragment_object;

		if (id != ResourceId.sDefaultValue) {
			fragment_object = fragmentResolver.resolveFragment(object, id);
		} else {
			fragment_object = fragmentResolver.resolveFragment(object, field.getName());
		}

		if (fragment_object == null) {
			throw new BindException(BindFragment.class, object.getClass(), field, "Fragment not found");
		}

		if (!field.getType().isAssignableFrom(fragment_object.getClass())) {
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
		}

		Reflection.setFieldValue(annotation, field, object, fragment_object);
	}

	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}
}