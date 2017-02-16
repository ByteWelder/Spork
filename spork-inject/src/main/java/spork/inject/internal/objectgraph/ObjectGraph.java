package spork.inject.internal.objectgraph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.inject.Qualifier;

import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;
import spork.inject.internal.objectgraph.modulenode.ModuleNode;
import spork.inject.internal.objectgraph.modulenode.InstanceCache;

public class ObjectGraph {
	private final ObjectGraphNode[] nodes;

	private ObjectGraph(ObjectGraphNode[] nodes) {
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

		return null;
	}

	public static class Builder {
		private final ArrayList<ObjectGraphNode> nodeList = new ArrayList<>(2);
		private final InstanceCache instanceCache = new InstanceCache();

		public Builder module(Object module) {
			nodeList.add(new ModuleNode(module, instanceCache));
			return this;
		}

		public ObjectGraph build() {
			ObjectGraphNode[] nodeArray = nodeList.toArray(new ObjectGraphNode[nodeList.size()]);
			return new ObjectGraph(nodeArray);
		}
	}
}
