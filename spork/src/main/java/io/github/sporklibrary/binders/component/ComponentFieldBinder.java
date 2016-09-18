package io.github.sporklibrary.binders.component;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.BindComponent;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.binders.FieldBinder;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.reflection.AnnotatedField;
import io.github.sporklibrary.reflection.AnnotatedFields;

/**
 * The default FieldBinder that binds field annotated with the Bind annotation.
 */
public class ComponentFieldBinder implements FieldBinder<BindComponent> {
    private final ComponentInstanceManager componentInstanceManager = new ComponentInstanceManager();

    @Override
    public Class<BindComponent> getAnnotationClass() {
        return BindComponent.class;
    }

    @Override
    public void bind(Object object, AnnotatedField<BindComponent> annotatedField, @Nullable Object[] modules) {
		// Bind with module system (uses @Provides annotation on methods)
		if (modules != null) {
			Object instance = Spork.getModuleManager().getObject(modules, annotatedField.getField().getType());

			if (instance == null) {
				throw new BindException(BindComponent.class, object.getClass(), annotatedField.getField(), "none of the modules provides an instance for " + annotatedField.getField().getType().getName());
			}

			AnnotatedFields.setValue(annotatedField, object, instance);
		} else {
			// Alternatively, let the instance manager create a new instance
			Object instance = componentInstanceManager.getInstance(object, annotatedField);
			AnnotatedFields.setValue(annotatedField, object, instance);
		}
	}
}
