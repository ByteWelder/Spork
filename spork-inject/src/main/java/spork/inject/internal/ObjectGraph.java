package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.Spork;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;
import spork.inject.internal.providers.InstanceCacheProvider;
import spork.inject.internal.providers.InstanceProvider;

public final class ObjectGraph {
	@Nullable
	private final ObjectGraph parentGraph;
	private final Map<InjectSignature, ObjectGraphNode> nodeMap;
	private final InstanceCache instanceCache = new InstanceCache();

	ObjectGraph(@Nullable ObjectGraph parentGraph, Map<InjectSignature, ObjectGraphNode> nodeMap) {
		this.parentGraph = parentGraph;
		this.nodeMap = nodeMap;
	}

	@Nullable
	<T> Provider<T> findProvider(Field targetField, Class<T> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, targetField);
		Nullability nullability = Nullability.create(targetField);
		InjectSignature injectSignature = new InjectSignature(targetType, nullability, qualifierAnnotation);
		return findProvider(injectSignature);
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
		if (node.getScopeId() == null && !injectSignature.hasQualifier()) {
			return (Provider<T>) new InstanceProvider(node, parameters);
		} else {
			return (Provider<T>) new InstanceCacheProvider(node, parameters, instanceCache);
		}

		// else convert to Provider
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
	 * A shortcut to Spork.bind(object, objectGraph).
	 * This binds all known annotations for the shared Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object) {
		Spork.bind(object, this);
	}

	/**
	 * A shortcut to spork.getBinder().bind(object, objectGraph)
	 * This binds all known annotations for the given Spork instance including spork-inject.
	 * @param object the object to bind
	 */
	public void inject(Object object, Spork spork) {
		spork.getBinder().bind(object, this);
	}
}
