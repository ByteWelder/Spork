package spork.android.internal.binders;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.android.BindFragment;
import spork.android.extension.FragmentResolver;
import spork.android.internal.utils.ResourceId;
import spork.FieldBinder;
import spork.internal.Reflection;

import static spork.BindFailedBuilder.bindFailedBuilder;

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
			throw bindFailedBuilder(BindFragment.class, "Fragment not found")
					.into(field)
					.build();
		}

		Reflection.setFieldValue(BindFragment.class, field, object, fragmentObject);
	}

	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}
}