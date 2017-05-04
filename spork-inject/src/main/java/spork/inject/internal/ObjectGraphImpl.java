package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.SporkInstance;
import spork.exceptions.BindContext;
import spork.exceptions.BindContextBuilder;
import spork.inject.ObjectGraph;
import spork.inject.internal.providers.CachedNodeProvider;
import spork.inject.internal.providers.NodeProvider;

public final class ObjectGraphImpl implements ObjectGraph {
	@Nullable
	private final ObjectGraphImpl parentGraph;
	private final Map<spork.inject.internal.reflection.InjectSignature, ObjectGraphNode> nodeMap;
	private final InstanceCache instanceCache;
	private final spork.inject.internal.reflection.ReflectionCache reflectionCache;
	private final Class<? extends Annotation> scopeAnnotationClass;

	/**
	 * @param parentGraph optional parent graph
	 * @param nodeMap a non-mutable map that maps InjectSignature to ObjectGraphNode
	 * @param reflectionCache retrieve (and cache) InjectSignature instance
	 * @param scopeAnnotationClass optional annotation that defines the scope of this ObjectGraph
	 */
	ObjectGraphImpl(@Nullable ObjectGraphImpl parentGraph, Map<spork.inject.internal.reflection.InjectSignature, ObjectGraphNode> nodeMap, spork.inject.internal.reflection.ReflectionCache reflectionCache, Class<? extends Annotation> scopeAnnotationClass) {
		this.parentGraph = parentGraph;
		this.nodeMap = nodeMap;
		this.instanceCache = new InstanceCache();
		this.reflectionCache = reflectionCache;
		this.scopeAnnotationClass = scopeAnnotationClass;
	}

	@Nullable
	Provider<?> findProvider(spork.inject.internal.reflection.InjectSignature injectSignature) throws ObjectGraphException {
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

			// We must have an ObjectGraph with an instance cache to target
			if (targetGraph == null) {
				BindContext bindContext = new BindContextBuilder(Inject.class)
						.suggest("When creating your ObjectGraphs, ensure that one has the required scope")
						.bindingInto(injectSignature.toString())
						.build();

				String message = "no ObjectGraph found that defines scope " + scope.annotationType().getName();

				throw new ObjectGraphException(message, bindContext);
			}

			return new CachedNodeProvider(targetGraph.instanceCache, node, parameters);
		}
	}

	@Nullable
	private ObjectGraphNode findNode(spork.inject.internal.reflection.InjectSignature injectSignature) {
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

	spork.inject.internal.reflection.ReflectionCache getReflectionCache() {
		return reflectionCache;
	}

	@Nullable
	Object[] getInjectableMethodParameters(Method method) throws ObjectGraphException {
		int parameterCount = method.getParameterTypes().length;
		if (parameterCount == 0) {
			return null;
		}

		// The following never returns null as there is guaranteed at least 1 method parameter
		spork.inject.internal.reflection.InjectSignature[] injectSignatures = getReflectionCache().getInjectSignatures(method);
		if (injectSignatures == null) {
			return null;
		}

		Object[] parameterInstances = new Object[parameterCount];

		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterCount; ++i) {
			Provider provider = findProvider(injectSignatures[i]);
			if (provider == null) {
				String signatureString = injectSignatures[i].toString();
				throw new ObjectGraphException("Invocation argument not found: " + signatureString);
			}

			boolean isProviderParameter = (parameterTypes[i] == Provider.class);
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
