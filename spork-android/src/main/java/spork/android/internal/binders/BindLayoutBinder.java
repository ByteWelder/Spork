package spork.android.internal.binders;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import spork.android.BindLayout;
import spork.BindException;
import spork.interfaces.TypeBinder;

public class BindLayoutBinder implements TypeBinder<BindLayout> {

	@Override
	public void bind(Object object, BindLayout annotation, Class<?> annotatedType, Object... parameters) {
		int layoutResourceId = annotation.value();

		if (Activity.class.isAssignableFrom(object.getClass())) {
			((Activity) object).setContentView(layoutResourceId);
		} else if (ViewGroup.class.isAssignableFrom(object.getClass())) {
			ViewGroup viewGroup = (ViewGroup) object;
			LayoutInflater.from(viewGroup.getContext()).inflate(layoutResourceId, viewGroup);
		} else {
			throw new BindException(BindLayout.class, object.getClass(), "annotation can only be used with Activity or ViewGroup");
		}
	}

	@Override
	public Class<BindLayout> getAnnotationClass() {
		return BindLayout.class;
	}
}
