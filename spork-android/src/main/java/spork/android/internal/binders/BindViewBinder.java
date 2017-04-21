package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import spork.extension.FieldBinder;
import spork.android.BindView;
import spork.android.extension.ViewResolver;
import spork.android.internal.utils.Views;
import spork.internal.Reflection;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public class BindViewBinder implements FieldBinder<BindView> {
	private final ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void bind(Object object, BindView annotation, Field field, Object... parameters) {
		if (!View.class.isAssignableFrom(field.getType())) {
			throw bindFailedBuilder(BindView.class, "field is not a View")
					.into(field)
					.build();
		}

		View view = Views.getView(viewResolver, annotation.value(), field.getName(), object);

		Reflection.setFieldValue(BindView.class, field, object, view);
	}

	@Override
	public Class<BindView> getAnnotationClass() {
		return BindView.class;
	}
}