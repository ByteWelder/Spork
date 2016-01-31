package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.utils.ViewResolver;

import java.lang.reflect.Method;

public class BindClickBinder implements MethodBinder<BindClick>
{
	private class OnClickListener implements View.OnClickListener
	{
		private final AnnotatedMethod mAnnotatedMethod;

		private final Object mObject;

		public OnClickListener(AnnotatedMethod annotatedMethod, Object object)
		{
			mAnnotatedMethod = annotatedMethod;
			mObject = object;
		}

		@Override
		public void onClick(View v)
		{
			Class<?>[] parameter_types = mAnnotatedMethod.getMethod().getParameterTypes();

			if (parameter_types.length == 0)
			{
				AnnotatedMethods.invoke(mAnnotatedMethod, mObject);
			}
			else if (parameter_types.length == 1 && View.class.isAssignableFrom(parameter_types[0]))
			{
				AnnotatedMethods.invoke(mAnnotatedMethod, mObject, v);
			}
			else
			{
				throw new BindException(BindClick.class, v.getClass(), mAnnotatedMethod.getMethod(), "onClick failed because the method arguments must be either empty or accept a a single View type");
			}
		}
	}

	@Override
	public Class<BindClick> getAnnotationClass()
	{
		return BindClick.class;
	}

	@Override
	public void bind(final Object object, AnnotatedMethod<BindClick> annotatedMethod)
	{
		final @Nullable View view = getView(object, annotatedMethod);

		if (view == null)
		{
			throw new BindException(BindView.class, object.getClass(),annotatedMethod.getMethod(),  "View not found");
		}

		view.setOnClickListener(new OnClickListener(annotatedMethod, object));
	}

	private View getView(Object object, AnnotatedMethod<BindClick> annotatedField)
	{
		Method method = annotatedField.getMethod();

		if (Activity.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), method.getName(), (Activity)object);
		}
		else if (Fragment.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), method.getName(), (Fragment)object);
		}
		else if (View.class.isAssignableFrom(object.getClass()))
		{
			return ViewResolver.getView(annotatedField.getAnnotation().value(), method.getName(), (View)object);
		}
		else
		{
			throw new BindException(BindView.class, object.getClass(), method, "class must be View, Fragment or Activity");
		}
	}
}
