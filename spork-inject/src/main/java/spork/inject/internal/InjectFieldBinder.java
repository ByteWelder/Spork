package spork.inject.internal;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.inject.Provider;

import spork.extension.FieldBinder;
import spork.inject.Lazy;
import spork.inject.internal.providers.ProviderLazy;
import spork.internal.Reflection;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

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
		ObjectGraphImpl objectGraph = ObjectGraphImpls.findObjectGraph(parameters);
		if (objectGraph == null) {
			throw bindFailedBuilder(Inject.class, "no ObjectGraph specified in instance arguments of bind()")
					.into(field)
					.build();
		}

		Class<?> fieldType = field.getType();
		boolean fieldIsLazy = (fieldType == Lazy.class);
		boolean fieldIsProvider = (fieldType == Provider.class);

		// Determine the true type of the instance (so not Provider.class)
		Class<?> targetType = fieldIsProvider || fieldIsLazy
				? (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]
				: fieldType;

		InjectSignature injectSignature = objectGraph.getInjectSignatureCache().getInjectSignature(field, targetType);
		Provider<?> provider = objectGraph.findProvider(injectSignature);

		if (provider == null) {
			throw bindFailedBuilder(Inject.class, "none of the modules provides an instance for " + injectSignature.toString())
					.into(field)
					.build();
		}

		// Either set the provider instance or the real instance
		if (fieldIsProvider) {
			Reflection.setFieldValue(Inject.class, field, instance, provider);
		} else if (fieldIsLazy) {
			Reflection.setFieldValue(Inject.class, field, instance, new ProviderLazy<>(provider));
		} else {
			Object bindInstance = provider.get();
			Reflection.setFieldValue(Inject.class, field, instance, bindInstance);
		}
	}
}
