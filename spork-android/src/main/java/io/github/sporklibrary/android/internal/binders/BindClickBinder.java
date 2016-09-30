package io.github.sporklibrary.android.internal.binders;

import android.view.View;

import java.lang.reflect.Method;

import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.interfaces.ViewResolver;
import io.github.sporklibrary.android.internal.utils.Views;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.interfaces.MethodBinder;
import io.github.sporklibrary.internal.Reflection;

public class BindClickBinder implements MethodBinder<BindClick> {
	private ViewResolver viewResolver;

	public BindClickBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	private class OnClickListener implements View.OnClickListener {
		private final BindClick annotation;
		private final Method method;
		private final Object object;

		OnClickListener(BindClick annotation, Method method, Object object) {
			this.annotation = annotation;
			this.method = method;
			this.object = object;
		}

		@Override
		public void onClick(View v) {
			Class<?>[] parameter_types = method.getParameterTypes();

			if (parameter_types.length == 0) {
				Reflection.invokeMethod(annotation, method, object);
			} else if (parameter_types.length == 1 && View.class.isAssignableFrom(parameter_types[0])) {
				Reflection.invokeMethod(annotation, method, object, v);
			} else {
				throw new BindException(BindClick.class, v.getClass(), method, "onClick failed because the method arguments must be either empty or accept a single View type");
			}
		}
	}

	@Override
	public void bind(Object object, BindClick annotation, Method method, @Nullable Object[] modules) {
		final View view = Views.getView(viewResolver, annotation.value(), method.getName(), object);

		view.setOnClickListener(new OnClickListener(annotation, method, object));
	}

	@Override
	public Class<BindClick> getAnnotationClass() {
		return BindClick.class;
	}
}
