package io.github.sporklibrary.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.interfaces.ViewResolver;
import io.github.sporklibrary.android.internal.utils.Views;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.internal.reflection.AnnotatedField;
import io.github.sporklibrary.internal.reflection.AnnotatedFields;

public class BindViewBinder implements FieldBinder<BindView> {
	private ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public Class<BindView> getAnnotationClass() {
		return BindView.class;
	}

	@Override
	public void bind(final Object object, final AnnotatedField<BindView> annotatedField, @Nullable Object[] modules) {
		final View view = resolveView(object, annotatedField);

		AnnotatedFields.setValue(annotatedField, object, view);
	}

	private View resolveView(Object object, AnnotatedField<BindView> annotatedField) {
		Field field = annotatedField.getField();

		if (!View.class.isAssignableFrom(field.getType())) {
			throw new BindException(BindView.class, object.getClass(), field, "field is not a View");
		}

		return Views.getView(viewResolver, annotatedField.getAnnotation().value(), field.getName(), object);
	}
}