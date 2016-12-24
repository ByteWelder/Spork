package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.exceptions.BindException;
import spork.interfaces.FieldBinder;
import spork.internal.Reflection;

/**
 * The default FieldBinder that binds field annotated with the Inject annotation.
 */
public class InjectFieldBinder implements FieldBinder<Inject> {
	private final ModuleManager moduleManager;

	public InjectFieldBinder(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object instance, Inject annotation, Field targetField, Object[] parameters) {
		Class<?> fieldType = targetField.getType();

		// Bind with module system (uses @Provides annotation on methods)
		if (parameters.length == 0) {
			throw new BindException(Inject.class, instance.getClass(), targetField, "must use modules in Spork.bind(instance, ...) when using @Inject at " + fieldType.getName());
		}

		boolean fieldIsProvider = (fieldType == Provider.class);
		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = fieldIsProvider ? (Class<?>) ((ParameterizedType) targetField.getGenericType()).getActualTypeArguments()[0] : fieldType;
		Provider<?> provider = moduleManager.getProvider(targetField, targetType, parameters);

		if (provider == null) {
			throw new BindException(Inject.class, instance.getClass(), targetField, "none of the modules provides an instance for " + fieldType.getName());
		}

		// Either set the provider instance or the real instance
		if (fieldIsProvider) {
			Reflection.setFieldValue(annotation, targetField, instance, provider);
		} else {
			Object bindInstance = provider.get();
			Reflection.setFieldValue(annotation, targetField, instance, bindInstance);
		}
	}
}
