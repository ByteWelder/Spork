package spork.inject.internal;

import java.lang.reflect.Method;

import javax.annotation.Nullable;
import javax.inject.Provider;

public final class ObjectGraphs {
	private ObjectGraphs() {
	}

	@Nullable
	static ObjectGraph findObjectGraph(Object[] objects) {
		for (Object object : objects) {
			if (object.getClass() == ObjectGraph.class) {
				return (ObjectGraph) object;
			}
		}

		return null;
	}

	@Nullable
	static Object[] getInjectableMethodParameters(ObjectGraph objectGraph, Method method) throws spork.inject.internal.ObjectGraphException {
		int parameterCount = method.getParameterTypes().length;
		if (parameterCount == 0) {
			return null;
		}

		Object[] parameterInstances = new Object[parameterCount];

		for (int i = 0; i < parameterCount; ++i) {
			// retrieve provider
			Class<?> parameterClass = method.getParameterTypes()[i];
			InjectSignature injectSignature = new InjectSignature(
					parameterClass,
					method.getParameterAnnotations()[i],
					method.getGenericParameterTypes()[i]);

			Provider provider = objectGraph.findProvider(injectSignature);

			if (provider == null) {
				throw new ObjectGraphException("invocation argument not found: " + injectSignature.toString());
			}

			boolean parameterIsProvider = (parameterClass == Provider.class);

			// store provider or instance
			parameterInstances[i] = parameterIsProvider ? provider : provider.get();
		}

		return parameterInstances;
	}
}
