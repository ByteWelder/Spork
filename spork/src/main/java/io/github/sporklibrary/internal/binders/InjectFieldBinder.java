package io.github.sporklibrary.internal.binders;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
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
		// Bind with module system (uses @Provides annotation on methods)
		if (modules == null || modules.length == 0) {
			throw new BindException(Inject.class, object.getClass(), annotatedField.getField(), "must use modules in Spork.bind(instance, ...) when using @Inject at " + annotatedField.getField().getType().getName());
		}

		Object instance = Spork.getModuleManager().getObject(modules, annotatedField.getField().getType());

		if (instance == null) {
			throw new BindException(Inject.class, object.getClass(), annotatedField.getField(), "none of the modules provides an instance for " + annotatedField.getField().getType().getName());
		}

		AnnotatedFields.setValue(annotatedField, object, instance);
	}
}
