package io.github.sporklibrary.internal.inject;

import java.lang.reflect.Field;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.NonNull;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.internal.ModuleManager;
import io.github.sporklibrary.internal.reflection.AnnotatedField;
import io.github.sporklibrary.internal.reflection.AnnotatedFields;

/**
 * The default FieldBinder that binds field annotated with the Inject annotation.
 */
public class InjectFieldBinder implements FieldBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object object, AnnotatedField<Inject> annotatedField, @Nullable Object[] modules) {
		Field field = annotatedField.getField();
		Class<?> fieldType = field.getType();

		// Bind with module system (uses @Provides annotation on methods)
		if (modules == null || modules.length == 0) {
			throw new BindException(Inject.class, object.getClass(), field, "must use modules in Spork.bind(instance, ...) when using @Inject at " + fieldType.getName());
		}

		ModuleManager.Callable callable = Spork.getModuleManager().getCallable(modules, fieldType);

		if (callable == null) {
			throw new BindException(Inject.class, object.getClass(), field, "none of the modules provides an instance for " + fieldType.getName());
		}

		Object instance = callable.call();

		boolean isNullableAnnotated = field.getAnnotation(Nullable.class) != null;
		boolean isNonNullAnnotated = field.getAnnotation(NonNull.class) != null;

		if (!isNullableAnnotated && instance == null) {
			throw new BindException(Inject.class, object.getClass(), field, "field is not annotated as Nullable but module tries to inject null value");
		}

		if (isNonNullAnnotated && instance == null) {
			throw new BindException(Inject.class, object.getClass(), field, "field is annotated as NonNull but module tries to inject null value");
		}

		AnnotatedFields.setValue(annotatedField, object, instance);
	}
}
