package io.github.sporklibrary.binders;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import io.github.sporklibrary.annotations.BindClick;
import io.github.sporklibrary.annotations.BindView;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.utils.ViewResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClickMethodBinder implements MethodBinder<BindClick>
{
	private enum Target
	{
		ACTIVITY,
		FRAGMENT,
		VIEW,
		INVALID;

		/**
		 * @param object the object to validate
		 * @return the binding target for the provided object
		 */
		public static Target get(Object object)
		{
			if (Activity.class.isAssignableFrom(object.getClass()))
			{
				return ACTIVITY;
			}
			else if (Fragment.class.isAssignableFrom(object.getClass()))
			{
				return FRAGMENT;
			}
			else if (View.class.isAssignableFrom(object.getClass()))
			{
				return VIEW;
			}
			else
			{
				return INVALID;
			}
		}
	}

	private class OnClickListener implements View.OnClickListener
	{
		private final Method mMethod;

		private final Object mObject;

		public OnClickListener(Object object, Method method)
		{
			mMethod = method;
			mObject = object;
		}

		@Override
		public void onClick(View v)
		{
			Class<?>[] parameter_types = mMethod.getParameterTypes();

			if (parameter_types.length == 0)
			{
				boolean accessible = mMethod.isAccessible();

				try
				{
					mMethod.setAccessible(true);
					mMethod.invoke(mObject);
				}
				catch (IllegalAccessException e)
				{
					throw new BindException(BindClick.class, v.getClass(), "onClick failed because of an access issue", e);
				}
				catch (InvocationTargetException e)
				{
					throw new BindException(BindClick.class, v.getClass(), "onClick failed because of an invocation issue", e);
				}
				finally
				{
					mMethod.setAccessible(accessible);
				}
			}
			else if (parameter_types.length == 1 && View.class.isAssignableFrom(parameter_types[0]))
			{
				boolean accessible = mMethod.isAccessible();

				try
				{
					mMethod.setAccessible(true);
					mMethod.invoke(mObject, v);
				}
				catch (IllegalAccessException e)
				{
					throw new BindException(BindClick.class, v.getClass(), "onClick failed because of an access issue", e);
				}
				catch (InvocationTargetException e)
				{
					throw new BindException(BindClick.class, v.getClass(), "onClick failed because of an invocation issue", e);
				}
				finally
				{
					mMethod.setAccessible(accessible);
				}
			}
			else
			{
				throw new BindException(BindClick.class, v.getClass(), "onClick failed because the method arguments must be either empty or accept a a single View type");
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
		Target target = Target.get(object);

		BindClick annotation = annotatedMethod.getAnnotation();

		final Method method = annotatedMethod.getMethod();

		View view;

		switch (target)
		{
			case ACTIVITY:
				view = ViewResolver.getView(annotation.value(), method.getName(), (Activity) object);
				break;

			case FRAGMENT:
				view = ViewResolver.getView(annotation.value(), method.getName(), (Fragment) object);
				break;

			case VIEW:
				view = ViewResolver.getView(annotation.value(), method.getName(), (View) object);
				break;

			default:
				throw new BindException(BindClick.class, object.getClass(), "not compatible with " + object.getClass().getName());
		}

		if (view == null)
		{
			throw new BindException(BindView.class, object.getClass(), "View not found for " + method.getName());
		}

		view.setOnClickListener(new OnClickListener(object, method));
	}
}
