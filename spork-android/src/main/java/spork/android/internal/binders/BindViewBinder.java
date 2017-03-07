package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.Field;

import spork.android.BindView;
import spork.android.interfaces.ViewResolver;
import spork.android.internal.utils.Views;
import spork.BindException;
import spork.interfaces.FieldBinder;
import spork.internal.Reflection;

public class BindViewBinder implements FieldBinder<BindView> {
	private final ViewResolver viewResolver;

	public BindViewBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public void bind(Object object, BindView annotation, Field field, Object... parameters) {
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