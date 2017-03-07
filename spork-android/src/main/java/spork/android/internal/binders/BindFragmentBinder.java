package spork.android.internal.binders;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.android.BindFragment;
import spork.android.interfaces.FragmentResolver;
import spork.android.internal.utils.ResourceId;
import spork.BindException;
import spork.interfaces.FieldBinder;
import spork.internal.Reflection;

public class BindFragmentBinder implements FieldBinder<BindFragment> {
	private final FragmentResolver fragmentResolver;

	public BindFragmentBinder(FragmentResolver fragmentResolver) {
		this.fragmentResolver = fragmentResolver;
	}

	@Override
	public void bind(Object object, BindFragment annotation, Field field, Object... parameters) {
		int id = annotation.value();
		@Nullable Object fragmentObject;

		if (id == ResourceId.NONE) {
			fragmentObject = fragmentResolver.resolveFragment(object, field.getName());
		} else {
			fragmentObject = fragmentResolver.resolveFragment(object, id);
		}

		if (fragmentObject == null) {
			throw new BindException(BindFragment.class, object.getClass(), field, "Fragment not found");
		}

		if (!field.getType().isAssignableFrom(fragmentObject.getClass())) {
			throw new BindException(BindFragment.class, object.getClass(), field, "field is not a Fragment");
		}

		Reflection.setFieldValue(annotation, field, object, fragmentObject);
	}

	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}
}