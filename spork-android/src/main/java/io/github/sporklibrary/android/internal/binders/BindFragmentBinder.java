package io.github.sporklibrary.android.internal.binders;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindFragment;
import io.github.sporklibrary.android.interfaces.FragmentResolver;
import io.github.sporklibrary.android.internal.utils.ResourceId;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.internal.reflection.AnnotatedField;
import io.github.sporklibrary.internal.reflection.AnnotatedFields;

public class BindFragmentBinder implements FieldBinder<BindFragment> {
	private final FragmentResolver fragmentResolver;

	public BindFragmentBinder(FragmentResolver fragmentResolver) {
		this.fragmentResolver = fragmentResolver;
	}

	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<BindFragment> annotatedField, @Nullable Object[] modules) {
		Object fragment_object = resolveFragment(object, annotatedField);

		if (fragment_object == null) {
			throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "Fragment not found");
		}

		if (!annotatedField.getField().getType().isAssignableFrom(fragment_object.getClass())) {
			throw new BindException(BindFragment.class, object.getClass(), annotatedField.getField(), "field is not a Fragment");
		}

		AnnotatedFields.setValue(annotatedField, object, fragment_object);
	}

	@Nullable
	private Object resolveFragment(Object object, AnnotatedField<BindFragment> annotatedField) {
		BindFragment annotation = annotatedField.getAnnotation();
		int id = annotation.value();

		if (id != ResourceId.sDefaultValue) {
			return fragmentResolver.resolveFragment(object, id);
		} else {
			Field field = annotatedField.getField();
			return fragmentResolver.resolveFragment(object, field.getName());
		}
	}
}