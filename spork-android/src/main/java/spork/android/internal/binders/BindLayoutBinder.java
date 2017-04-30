package spork.android.internal.binders;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import spork.extension.TypeBinder;
import spork.android.BindLayout;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

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
			throw bindFailedBuilder(BindLayout.class, "BindLayout is not compatible with " + object.getClass().getName())
					.suggest("BindLayout only works with Activity or ViewGroup")
					.into(object.getClass())
					.build();
		}
	}

	@Override
	public Class<BindLayout> getAnnotationClass() {
		return BindLayout.class;
	}
}
