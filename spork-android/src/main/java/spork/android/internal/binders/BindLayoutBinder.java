package spork.android.internal.binders;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import spork.android.BindLayout;
import spork.exceptions.ExceptionMessageBuilder;
import spork.exceptions.BindFailed;
import spork.extension.TypeBinder;

public class BindLayoutBinder implements TypeBinder<BindLayout> {

	@Override
	public void bind(Object object, BindLayout annotation, Class<?> annotatedType, Object... parameters) throws BindFailed{
		int layoutResourceId = annotation.value();

		if (Activity.class.isAssignableFrom(object.getClass())) {
			((Activity) object).setContentView(layoutResourceId);
		} else if (ViewGroup.class.isAssignableFrom(object.getClass())) {
			ViewGroup viewGroup = (ViewGroup) object;
			LayoutInflater.from(viewGroup.getContext()).inflate(layoutResourceId, viewGroup);
		} else {
			String baseMessage = "BindLayout is not compatible with " + object.getClass().getName();
			String message = new ExceptionMessageBuilder(baseMessage)
					.annotation(BindLayout.class)
					.suggest("BindLayout only works with Activity or ViewGroup")
					.bindingInto(object.getClass())
					.build();

			throw new BindFailed(message);
		}
	}

	@Override
	public Class<BindLayout> getAnnotationClass() {
		return BindLayout.class;
	}
}
