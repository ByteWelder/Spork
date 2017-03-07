package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.BindException;
import spork.inject.Lazy;
import spork.inject.internal.providers.LazyProvider;
import spork.interfaces.FieldBinder;
import spork.internal.Reflection;

/**
 * The default FieldBinder that binds field annotated with the Inject annotation.
 */
public class InjectFieldBinder implements FieldBinder<Inject> {

	@Override
	public Class<Inject> getAnnotationClass() {
		return Inject.class;
	}

	@Override
	public void bind(Object instance, Inject annotation, Field field, Object... parameters) {
		ObjectGraph objectGraph = ObjectGraphs.findObjectGraph(parameters);
		if (objectGraph == null) {
			throw new BindException(Inject.class, instance.getClass(), field, "no ObjectGraph specified in instance arguments of Spork.bind() when injecting " + instance.getClass().getName());
		}

		Class<?> fieldType = field.getType();
		boolean fieldIsLazy = field.isAnnotationPresent(Lazy.class);
		boolean fieldIsProvider = (fieldType == Provider.class);

		if (fieldIsLazy && !fieldIsProvider) {
			throw new BindException(Inject.class, instance.getClass(), field, "Lazy annotation can only be used with Provider field");
		}

		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = fieldIsProvider ? (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] : fieldType;

		InjectSignature injectSignature = objectGraph.getInjectSignatureCache().getInjectSignature(field, targetType);
		Provider<?> provider = objectGraph.findProvider(injectSignature);

		if (provider == null) {
			throw new BindException(Inject.class, instance.getClass(), field, "none of the modules provides an instance for " + fieldType.getName());
		}

		// Either set the provider instance or the real instance
		if (fieldIsProvider) {
			if (fieldIsLazy) {
				Reflection.setFieldValue(annotation, field, instance, new LazyProvider<>(provider));
			} else {
				Reflection.setFieldValue(annotation, field, instance, provider);
			}
		} else {
			Object bindInstance = provider.get();
			Reflection.setFieldValue(annotation, field, instance, bindInstance);
		}
	}
}
