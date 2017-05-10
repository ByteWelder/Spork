package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import spork.android.BindView;
import spork.android.extension.ViewResolver;
import spork.android.internal.utils.Views;
import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.extension.FieldBinder;

public class BindViewBinder implements FieldBinder<BindView> {
	private final ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void bind(Object object, BindView annotation, Field field, Object... parameters) throws BindFailed {
		if (!View.class.isAssignableFrom(field.getType())) {
			String message = new ExceptionMessageBuilder("Field is not a View")
					.annotation(BindView.class)
					.bindingInto(field)
					.build();
			throw new BindFailed(message);
		}

		try {
			View view = Views.getView(viewResolver, annotation.value(), field.getName(), object);

			field.setAccessible(true);
			field.set(object, view);
		} catch (IllegalAccessException caught) {
			String message = new ExceptionMessageBuilder("Failed to access " + field.toString())
					.suggest("There might be a concurrency problem or you are trying to access a final static Field.")
					.annotation(BindView.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		} catch (Exception caught) {
			String message = new ExceptionMessageBuilder("Failed to resolve View")
					.annotation(BindView.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		} finally {
			field.setAccessible(false);
		}
	}

	@Override
	public Class<BindView> getAnnotationClass() {
		return BindView.class;
	}
}