package spork.android.internal.binders;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import spork.android.annotations.BindLayout;
import spork.exceptions.BindException;
import spork.interfaces.TypeBinder;

public class BindLayoutBinder implements TypeBinder<BindLayout> {

	@Override
	public void bind(Object object, BindLayout annotation, Class<?> annotatedType, Object[] modules) {
		int layout_resource_id = annotation.value();

		if (Activity.class.isAssignableFrom(object.getClass())) {
			((Activity) object).setContentView(layout_resource_id);
		} else if (ViewGroup.class.isAssignableFrom(object.getClass())) {
			ViewGroup view_group = (ViewGroup) object;
			LayoutInflater.from(view_group.getContext()).inflate(layout_resource_id, view_group);
		} else {
			throw new BindException(BindLayout.class, object.getClass(), "annotation can only be used with Activity or ViewGroup");
		}
	}

	@Override
	public Class<BindLayout> getAnnotationClass() {
		return BindLayout.class;
	}
}
