package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;

import spork.exceptions.BindException;
import spork.interfaces.FieldBinder;
import spork.internal.Callable;

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

		boolean isProvider = (fieldType == Provider.class);
		Class<?> targetType = isProvider ? (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] : fieldType;

		Callable<?> callable = moduleManager.getCallable(modules, targetType);

		if (callable == null) {
			throw new BindException(Inject.class, instance.getClass(), field, "none of the modules provides an instance for " + fieldType.getName());
		}

		if (isProvider) {
			CallableProvider<?> callableProvider = new CallableProvider<>(callable);
			spork.internal.Reflection.setFieldValue(annotation, field, instance, callableProvider);
		} else {
			Object bindInstance = call(callable, field, instance);
			spork.internal.Reflection.setFieldValue(annotation, field, instance, bindInstance);
		}
	}

	private Object call(spork.internal.Callable<?> callable, Field field, Object object) {
		Object instance = callable.call();

		boolean isNullableAnnotated = field.getAnnotation(Nullable.class) != null;
		boolean isNonNullAnnotated = field.getAnnotation(Nonnull.class) != null;

		if (!isNullableAnnotated && instance == null) {
			throw new BindException(Inject.class, object.getClass(), field, "field is not annotated as Nullable but module tries to inject null value");
		}

		if (isNonNullAnnotated && instance == null) {
			throw new BindException(Inject.class, object.getClass(), field, "field is annotated as NonNull but module tries to inject null value");
		}

		return instance;
	}
}
