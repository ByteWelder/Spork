package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
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
	public void bind(Object instance, Inject annotation, Field field, @Nullable Object[] modules) {
		Class<?> fieldType = field.getType();

		// Bind with module system (uses @Provides annotation on methods)
		if (modules == null || modules.length == 0) {
			throw new BindException(Inject.class, instance.getClass(), field, "must use modules in Spork.bind(instance, ...) when using @Inject at " + fieldType.getName());
		}

		boolean fieldIsProvider = (fieldType == Provider.class);
		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = fieldIsProvider ? (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] : fieldType;
		// Retrieve a provider from the module methods
		Provider<?> provider = moduleManager.getProvider(field, modules, targetType);

		if (provider == null) {
			throw new BindException(Inject.class, instance.getClass(), field, "none of the modules provides an instance for " + fieldType.getName());
		}

		// Either set the provider instance or the real instance
		if (fieldIsProvider) {
			Reflection.setFieldValue(annotation, field, instance, provider);
		} else {
			Object bindInstance = provider.get();
			Reflection.setFieldValue(annotation, field, instance, bindInstance);
		}
	}
}
