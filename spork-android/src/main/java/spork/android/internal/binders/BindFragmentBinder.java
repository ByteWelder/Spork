package spork.android.internal.binders;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.android.annotations.BindFragment;
import spork.android.interfaces.FragmentResolver;
import spork.android.internal.utils.ResourceId;
import spork.BindException;
import spork.FieldBinder;
import spork.internal.Reflection;

public class BindFragmentBinder implements FieldBinder<BindFragment> {
	private final FragmentResolver fragmentResolver;

	public BindFragmentBinder(FragmentResolver fragmentResolver) {
		this.fragmentResolver = fragmentResolver;
	}

	@Override
	public void bind(Object object, BindFragment annotation, Field field, Object[] modules) {
		int id = annotation.value();
		@Nullable Object fragment_object;

		if (id != ResourceId.sDefaultValue) {
			fragment_object = fragmentResolver.resolveFragment(object, id);
		} else {
			fragment_object = fragmentResolver.resolveFragment(object, field.getName());
		}

		if (fragment_object == null) {
			throw new BindException(BindFragment.class, object.getClass(), field, "Fragment not found");
		}

		if (!field.getType().isAssignableFrom(fragment_object.getClass())) {
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
		}

		Reflection.setFieldValue(annotation, field, object, fragment_object);
	}

	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}
}