package spork.android.internal.binders;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import spork.android.BindFragment;
import spork.android.extension.FragmentResolver;
import spork.android.internal.utils.ResourceId;
import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.extension.FieldBinder;

public class BindFragmentBinder implements FieldBinder<BindFragment> {
	private final FragmentResolver fragmentResolver;

	public BindFragmentBinder(FragmentResolver fragmentResolver) {
		this.fragmentResolver = fragmentResolver;
	}

	@Override
	public void bind(Object object, BindFragment annotation, Field field, Object... parameters) throws BindFailed {
		int id = annotation.value();
		@Nullable Object fragmentObject = resolveFragment(id, object, field);

		if (fragmentObject == null) {
			String message = new ExceptionMessageBuilder("Fragment not found")
					.annotation(BindFragment.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
		}

		try {
			field.setAccessible(true);
			field.set(object, fragmentObject);
		} catch (IllegalAccessException caught) {
			String message = new ExceptionMessageBuilder("Failed to access " + field.toString())
					.suggest("There might be a concurrency problem or you are trying to access a final static Field.")
					.annotation(BindFragment.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		} finally {
			field.setAccessible(false);
		}
	}

	@Nullable
	private Object resolveFragment(int id, Object object, Field field) throws BindFailed {
		try {
			if (id == ResourceId.NONE) {
				return fragmentResolver.resolveFragment(object, field.getName());
			} else {
				return fragmentResolver.resolveFragment(object, id);
			}
		} catch (Exception caught) {
			String message = new ExceptionMessageBuilder("Failed to resolve Fragment for field")
					.annotation(BindFragment.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		}
	}
	@Override
	public Class<BindFragment> getAnnotationClass() {
		return BindFragment.class;
	}
}