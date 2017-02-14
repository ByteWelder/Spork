package spork.inject.internal.objectgraph.nodes;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.exceptions.BindException;
import spork.inject.internal.objectgraph.Annotations;
import spork.inject.internal.objectgraph.InjectSignature;
import spork.inject.internal.objectgraph.Nullability;
import spork.inject.internal.objectgraph.ObjectGraph;

public class ModuleMethodInvoker<T> {
	private ObjectGraph objectGraph;
	private final Object module;
	private final Method method;
	private final Class<?> targetType;

	ModuleMethodInvoker(ObjectGraph objectGraph, Object module, Method method, Class<T> targetType) {
		this.objectGraph = objectGraph;
		this.module = module;
		this.method = method;
		this.targetType = targetType;
	}

	public T invoke() {
		@Nullable Object[] methodParameters = collectMethodParameters(method);
		return invokeMethod(methodParameters);
	}

	@SuppressWarnings("unchecked")
	private T invokeMethod(@Nullable Object[] params) {
		try {
			return (T) method.invoke(module, params);
		} catch (IllegalAccessException e) {
			throw new BindException(Inject.class, module.getClass(), targetType, "method \"" + method.getName() + "\" on module " + module.getClass().getName() + " is not public", e);
		} catch (Exception e) {
			throw new BindException(Inject.class, module.getClass(), targetType, "failed to invoke method \"" + method.getName() + "\" on module " + module.getClass().getName(), e);
		}
	}

	private @Nullable Object[] collectMethodParameters(Method method) {
		int parameterCount = method.getParameterTypes().length;
		if (parameterCount == 0) {
			return null;
		}

		List<InjectSignature> injectSignatures = getInjectSignatures(method);

		// TODO: implement support for multiple arguments
		Provider[] providers = getProviders(injectSignatures);

		return getParameterArguments(method, providers);
	}

	private List<InjectSignature> getInjectSignatures(Method method) {
		int parameterCount = method.getParameterTypes().length;
		List<InjectSignature> injectSignatures = new ArrayList<>(parameterCount);

		for (int i = 0; i < parameterCount; ++i) {
			Class<?> parameterClass = method.getParameterTypes()[i];
			Annotation[] annotations = method.getParameterAnnotations()[i];
			Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, annotations);
			Nullability nullability = Nullability.create(annotations);

			boolean parameterIsProvider = (parameterClass == Provider.class);
			// Determine the true type of the instance (so not Provider.class)
			Type genericParameterType = method.getGenericParameterTypes()[i];
			Class<?> targetType = parameterIsProvider ? (Class<?>) ((ParameterizedType) genericParameterType).getActualTypeArguments()[0] : parameterClass;

			injectSignatures.add(new InjectSignature(targetType, nullability, qualifierAnnotation));
		}

		return injectSignatures;
	}

	private Provider[] getProviders(List<InjectSignature> injectSignatures) {
		Provider[] result = new Provider[injectSignatures.size()];

		for (int i = 0; i < result.length; ++i) {
			InjectSignature injectSignature = injectSignatures.get(i);
			result[i] = objectGraph.findProvider(injectSignature);
		}

		return result;
	}

	private Object[] getParameterArguments(Method method, Provider[] providers) {
		Object[] instances = new Object[providers.length];

		for (int i = 0; i < instances.length; ++i) {
			Class<?> parameterClass = method.getParameterTypes()[i];
			boolean parameterIsProvider = (parameterClass == Provider.class);

			if (parameterIsProvider) {
				instances[i] = providers[i];
			} else {
				instances[i] = providers[i].get();
			}
		}

		return instances;
	}
}
