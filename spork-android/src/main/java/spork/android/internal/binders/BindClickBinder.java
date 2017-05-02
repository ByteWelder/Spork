package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import spork.android.BindClick;
import spork.android.extension.ViewResolver;
import spork.android.internal.utils.Views;
import spork.exceptions.BindContext;
import spork.exceptions.BindContextBuilder;
import spork.exceptions.BindFailed;
import spork.exceptions.SporkRuntimeException;
import spork.extension.MethodBinder;
import spork.internal.Reflection;

public class BindClickBinder implements MethodBinder<BindClick> {
	private final ViewResolver viewResolver;

	public BindClickBinder(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	private static class BindClickListener implements View.OnClickListener {
		private final BindClick annotation;
		private final Method method;
		private final Object object;

		BindClickListener(BindClick annotation, Method method, Object object) {
			this.annotation = annotation;
			this.method = method;
			this.object = object;
		}

		private BindContextBuilder getBindContextBuilder(View view) {
			return new BindContextBuilder(BindClick.class)
					.bindingFrom(view.getClass())
					.bindingInto(method);
		}

		@Override
		public void onClick(View v) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			if (parameterTypes.length == 0) {
				try {
					Reflection.invokeMethod(method, object);
				} catch (InvocationTargetException e) {
					BindContext context = getBindContextBuilder(v).build();
					throw new SporkRuntimeException("failed to invoke click method", context);
				}
			} else if (parameterTypes.length == 1 && View.class.isAssignableFrom(parameterTypes[0])) {
				try {
					Reflection.invokeMethod(method, object, v);
				} catch (InvocationTargetException e) {
					BindContext context = getBindContextBuilder(v).build();
					throw new SporkRuntimeException("failed to invoke click method", context);
				}
			} else {
				BindContext context = getBindContextBuilder(v)
						.suggest("method arguments must be a View type (e.g. View, Button, etc.)")
						.build();
				throw new SporkRuntimeException("onClick() failed because the method arguments are invalid", context);
			}
		}
	}

	@Override
	public void bind(Object object, BindClick annotation, Method method, Object... parameters) throws BindFailed {
		View view = getView(object, annotation, method);
		view.setOnClickListener(new BindClickListener(annotation, method, object));
	}

	private View getView(Object object, BindClick annotation, Method method) throws BindFailed {
		try {
			return Views.getView(viewResolver, annotation.value(), method.getName(), object);
		} catch (Exception caught) {
			BindContext bindContext = new BindContextBuilder(BindClick.class)
					.bindingInto(method)
					.build();

			throw new BindFailed("failed to resolve View for method", caught, bindContext);
		}
	}

	@Override
	public Class<BindClick> getAnnotationClass() {
		return BindClick.class;
	}
}
