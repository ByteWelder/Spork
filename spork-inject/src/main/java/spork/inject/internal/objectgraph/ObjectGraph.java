package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.Spork;
import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public final class ObjectGraph {
	@Nullable
	private final ObjectGraph parentGraph;
	private final ObjectGraphNode[] nodes;

	ObjectGraph(@Nullable ObjectGraph parentGraph, ObjectGraphNode[] nodes) {
		this.parentGraph = parentGraph;
		this.nodes = nodes;
	}

	@Nullable
	public <T> Provider<T> findProvider(Field targetField, Class<T> targetType) {
		Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, targetField);
		Nullability nullability = Nullability.create(targetField);
		InjectSignature injectSignature = new InjectSignature(targetType, nullability, qualifierAnnotation);

		return findProvider(injectSignature);
	}

	@Nullable
	public <T> Provider<T> findProvider(InjectSignature injectSignature) {
		for (ObjectGraphNode node : nodes) {
			Provider<T> provider = node.findProvider(this, injectSignature);

			if (provider != null) {
				return provider;
			}
		}

		if (parentGraph != null) {
			return parentGraph.findProvider(injectSignature);
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
