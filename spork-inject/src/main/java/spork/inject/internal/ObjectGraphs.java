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

		InjectSignature[] injectSignatures = objectGraph.getInjectSignatureCache().getInjectSignatures(method);

		if (injectSignatures.length == 0) {
			return null;
		}

		Object[] parameterInstances = new Object[parameterCount];

		for (int i = 0; i < parameterCount; ++i) {
			Provider provider = objectGraph.findProvider(injectSignatures[i]);
			if (provider == null) {
				throw new ObjectGraphException("invocation argument not found: " + injectSignatures[i].toString());
			}
			boolean isProviderParameter = (method.getParameterTypes()[i] == Provider.class);
			parameterInstances[i] = isProviderParameter ? provider : provider.get();
		}

		return parameterInstances;
	}
}
