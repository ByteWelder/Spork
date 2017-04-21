package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import spork.Spork;
import spork.SporkInstance;
import spork.inject.internal.providers.InstanceMapProvider;
import spork.inject.internal.providers.InstanceProvider;

import static spork.internal.BindFailedBuilder.bindFailedBuilder;

public final class ObjectGraph {
	@Nullable
	private final ObjectGraph parentGraph;
	private final Map<InjectSignature, ObjectGraphNode> nodeMap;
	private final Map<InjectSignature, Object> instanceMap = new ConcurrentHashMap<>();
	private final InjectSignatureCache injectSignatureCache;
	private final Class<? extends Annotation> scopeAnnotationClass;

	ObjectGraph(@Nullable ObjectGraph parentGraph, Map<InjectSignature, ObjectGraphNode> nodeMap, InjectSignatureCache injectSignatureCache, Class<? extends Annotation> scopeAnnotationClass) {
		this.parentGraph = parentGraph;
		this.nodeMap = nodeMap;
		this.injectSignatureCache = injectSignatureCache;
		this.scopeAnnotationClass = scopeAnnotationClass;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	<T> Provider<T> findProvider(InjectSignature injectSignature) {
		ObjectGraphNode node = findNode(injectSignature);

		if (node == null) {
			return null;
		}

		Object[] parameters = node.collectParameters(this);

		// No scope and no qualifier means a new instance per injection
		if (node.getScope() == null && !injectSignature.hasQualifier()) {
			return (Provider<T>) new InstanceProvider(node, parameters);
		} else {
			Annotation scope = node.getScope();
			ObjectGraph scopedGraph = (scope != null)
					? findObjectGraph(scope.annotationType())
					: this;

			if (scopedGraph == null) {
				String message = "no ObjectGraph found that defines scope " + scope.annotationType().getName();
				throw bindFailedBuilder(Inject.class, message)
						.into(injectSignature.getType())
						.build();
			}

			return (Provider<T>) new InstanceMapProvider(scopedGraph.instanceMap, node, parameters);
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

	/**
	 * A shortcut to SporkInstance.bind(object, objectGraph).
	 * This binds all known annotations for the shared Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object) {
		SporkInstance.bind(object, this);
	}

	/**
	 * A shortcut to spork.getBinder().bind(object, objectGraph)
	 * This binds all known annotations for the given Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object, Spork spork) {
		spork.bind(object, this);
	}

	InjectSignatureCache getInjectSignatureCache() {
		return injectSignatureCache;
	}

	private @Nullable ObjectGraph findObjectGraph(Class<? extends Annotation> scopeAnnotationClass) {
		if (this.scopeAnnotationClass == scopeAnnotationClass) {
			return this;
		} else if (parentGraph != null) {
			return parentGraph.findObjectGraph(scopeAnnotationClass);
		} else {
			return null;
		}
	}
}
