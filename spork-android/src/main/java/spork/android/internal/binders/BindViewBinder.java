package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import spork.exceptions.ExceptionMessageBuilder;
import spork.exceptions.BindFailed;
import spork.extension.FieldBinder;
import spork.android.BindView;
import spork.android.extension.ViewResolver;
import spork.android.internal.utils.Views;
import spork.internal.Reflection;

public class BindViewBinder implements FieldBinder<BindView> {
	private final ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void bind(Object object, BindView annotation, Field field, Object... parameters) throws BindFailed {
		if (!View.class.isAssignableFrom(field.getType())) {
			String message = getExceptionMessage("Field is not a View", field);
			throw new BindFailed(message);
		}

		try {
			View view = Views.getView(viewResolver, annotation.value(), field.getName(), object);
			Reflection.setFieldValue(field, object, view);
		} catch (Exception caught) {
			String message = getExceptionMessage("Failed to resolve View", field);
			throw new BindFailed(message, caught);
		}
	}

	private String getExceptionMessage(String baseMessage, Field field) {
		return new ExceptionMessageBuilder(baseMessage)
				.annotation(BindView.class)
				.bindingInto(field)
				.build();
	}

	@Override
	public Class<BindView> getAnnotationClass() {
		return BindView.class;
	}
}