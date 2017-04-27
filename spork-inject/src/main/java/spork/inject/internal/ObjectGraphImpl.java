package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.SporkInstance;
import spork.inject.ObjectGraph;
import spork.inject.internal.providers.CachedNodeProvider;
import spork.inject.internal.providers.NodeProvider;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public final class ObjectGraphImpl implements ObjectGraph {
	@Nullable
	private final ObjectGraphImpl parentGraph;
	private final Map<InjectSignature, ObjectGraphNode> nodeMap;
	private final Map<InjectSignature, Object> instanceMap = new ConcurrentHashMap<>();
	private final InjectSignatureCache injectSignatureCache;
	private final Class<? extends Annotation> scopeAnnotationClass;

	ObjectGraphImpl(@Nullable ObjectGraphImpl parentGraph, Map<InjectSignature, ObjectGraphNode> nodeMap, InjectSignatureCache injectSignatureCache, Class<? extends Annotation> scopeAnnotationClass) {
		this.parentGraph = parentGraph;
		this.nodeMap = nodeMap;
		this.injectSignatureCache = injectSignatureCache;
		this.scopeAnnotationClass = scopeAnnotationClass;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	Provider<?> findProvider(InjectSignature injectSignature) {
		ObjectGraphNode node = findNode(injectSignature);

		if (node == null) {
			return null;
		}

		Object[] parameters = node.collectParameters(this);

		// No scope and no qualifier means a new instance per injection
		if (node.getScope() == null && !injectSignature.hasQualifier()) {
			return new NodeProvider(node, parameters);
		} else {
			// Retrieve the target ObjectGraph that holds the instances for the required Provider.
			// The graph will either be the one belonging to specific a scope or otherwise it is "this" graph.
			Annotation scope = node.getScope();
			ObjectGraphImpl targetGraph = (scope != null)
					? findObjectGraph(scope.annotationType())
					: this;

			// We must have an ObjectGraph with an instanceMap to target
			if (targetGraph == null) {
				String message = "no ObjectGraph found that defines scope " + scope.annotationType().getName();
				throw bindFailedBuilder(Inject.class, message)
						.into(injectSignature.toString())
						.build();
			}

			return new CachedNodeProvider(targetGraph.instanceMap, node, parameters);
		}
	}

	@Nullable
	private ObjectGraphNode findNode(InjectSignature injectSignature) {
		ObjectGraphNode node = nodeMap.get(injectSignature);
		if (node != null) {
			return node;
		} else if (parentGraph != null) {
			return parentGraph.findNode(injectSignature);
		} else {
			return null;
		}
	}

	@Override
	public void inject(Object object) {
		Spork.bind(object, this);
	}

	@Override
	public void inject(Object object, SporkInstance spork) {
		spork.bind(object, this);
	}

	InjectSignatureCache getInjectSignatureCache() {
		return injectSignatureCache;
	}

	@Nullable
	Object[] getInjectableMethodParameters(Method method) throws ObjectGraphException {
		int parameterCount = method.getParameterTypes().length;
		if (parameterCount == 0) {
			return null;
		}

		// The following never returns null as there is guaranteed at least 1 method parameter
		InjectSignature[] injectSignatures = getInjectSignatureCache().getInjectSignatures(method);
		if (injectSignatures == null) {
			return null;
		}

		Object[] parameterInstances = new Object[parameterCount];

		for (int i = 0; i < parameterCount; ++i) {
			Provider provider = findProvider(injectSignatures[i]);
			if (provider == null) {
				throw new ObjectGraphException("invocation argument not found: " + injectSignatures[i].toString());
			}
			boolean isProviderParameter = (method.getParameterTypes()[i] == Provider.class);
			parameterInstances[i] = isProviderParameter ? provider : provider.get();
		}

		return parameterInstances;
	}

	@Nullable
	private ObjectGraphImpl findObjectGraph(Class<? extends Annotation> scopeAnnotationClass) {
		if (this.scopeAnnotationClass == scopeAnnotationClass) {
			return this;
		} else if (parentGraph != null) {
			return parentGraph.findObjectGraph(scopeAnnotationClass);
		} else {
			return null;
		}
	}
}
