package io.github.sporklibrary.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import io.github.sporklibrary.android.annotations.BindView;
import io.github.sporklibrary.android.interfaces.ViewResolver;
import io.github.sporklibrary.android.internal.utils.Views;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.internal.Reflection;

public class BindViewBinder implements FieldBinder<BindView> {
	private ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void bind(Object object, BindView annotation, Field field, Object[] modules) {
		if (!View.class.isAssignableFrom(field.getType())) {
			throw new BindException(BindView.class, object.getClass(), field, "field is not a View");
		}

		View view = Views.getView(viewResolver, annotation.value(), field.getName(), object);

		Reflection.setFieldValue(annotation, field, object, view);
	}

	@Override
	public Class<BindView> getAnnotationClass() {
		return BindView.class;
	}
}