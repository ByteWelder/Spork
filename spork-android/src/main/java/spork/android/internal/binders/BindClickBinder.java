package spork.android.internal.binders;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import spork.android.BindClick;
import spork.android.extension.ViewResolver;
import spork.android.internal.utils.Views;
import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.exceptions.SporkRuntimeException;
import spork.exceptions.UnexpectedException;
import spork.extension.MethodBinder;

public final class BindClickBinder implements MethodBinder<BindClick> {
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

		private ExceptionMessageBuilder getExceptionMessageBuilder(String baseMessage, View view) {
			return new ExceptionMessageBuilder(baseMessage)
					.annotation(BindClick.class)
					.bindingFrom(view.getClass())
					.bindingInto(method);
		}

		@Override
		public void onClick(View view) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			try {
				method.setAccessible(true);

				if (parameterTypes.length == 0) {
					method.invoke(object);
				} else if (parameterTypes.length == 1 && View.class.isAssignableFrom(parameterTypes[0])) {
					method.invoke(object, view);
				} else {
					String message = getExceptionMessageBuilder("onClick() failed because the method arguments are invalid", view)
							.suggest("method arguments must be a View type (e.g. View, Button, etc.)")
							.build();
					throw new SporkRuntimeException(message);
				}
			} catch (IllegalAccessException caught) {
				throw new UnexpectedException("Failed to access a Method that was previously made accessible. Maybe there is a concurrency problem?", caught);
			} catch (InvocationTargetException caught) {
				String message = getExceptionMessageBuilder("Failed to invoke click method", view).build();
				throw new SporkRuntimeException(message, caught);
			} finally {
				method.setAccessible(false);
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
			String message = new ExceptionMessageBuilder("Failed to resolve View for method")
					.annotation(BindClick.class)
					.bindingInto(method)
					.build();
			throw new BindFailed(message, caught);
		}
	}

	@Override
	public Class<BindClick> getAnnotationClass() {
		return BindClick.class;
	}
}
