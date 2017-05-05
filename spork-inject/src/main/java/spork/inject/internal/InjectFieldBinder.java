package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.exceptions.BindFailed;
import spork.exceptions.ExceptionMessageBuilder;
import spork.extension.FieldBinder;
import spork.inject.Lazy;
import spork.inject.internal.providers.ProviderLazy;
import spork.inject.internal.reflection.Classes;
import spork.inject.internal.reflection.InjectSignature;
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
	public void bind(Object instance, Inject annotation, Field field, Object... parameters) throws BindFailed {
		ObjectGraphImpl objectGraph = Classes.findFirstInstanceOfType(ObjectGraphImpl.class, parameters);
		if (objectGraph == null) {
			String message = new ExceptionMessageBuilder("No ObjectGraph specified in instance arguments of bind()")
					.annotation(Inject.class)
					.suggest("call Spork.bind(target, objectGraph")
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
		}

		Class<?> fieldType = field.getType();
		boolean fieldIsLazy = (fieldType == Lazy.class);
		boolean fieldIsProvider = (fieldType == Provider.class);

		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = fieldIsProvider || fieldIsLazy
				? (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]
				: fieldType;

		InjectSignature injectSignature = objectGraph.getReflectionCache().getInjectSignature(field, targetType);
		Provider<?> provider;

		try {
			provider = objectGraph.findProvider(injectSignature);
		} catch (ObjectGraphException caught) {
			String message = new ExceptionMessageBuilder("Failed to resolve provider for " + injectSignature.toString())
					.annotation(Inject.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message, caught);
		}

		if (provider == null) {
			String message = new ExceptionMessageBuilder("None of the modules provides an instance for " + injectSignature.toString())
					.annotation(Inject.class)
					.bindingInto(field)
					.build();

			throw new BindFailed(message);
		}

		// Either set the provider instance or the real instance
		if (fieldIsProvider) {
			Reflection.setFieldValue(field, instance, provider);
		} else if (fieldIsLazy) {
			Reflection.setFieldValue(field, instance, new ProviderLazy<>(provider));
		} else {
			Object bindInstance = provider.get();
			Reflection.setFieldValue(field, instance, bindInstance);
		}
	}
}
