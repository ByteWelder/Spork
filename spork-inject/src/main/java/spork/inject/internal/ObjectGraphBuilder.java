package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Qualifier;

import spork.inject.Provides;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public class ObjectGraphBuilder {
	@Nullable
	private final ObjectGraph parentGraph;
	private final List<Object> modules = new ArrayList<>(4);

	public ObjectGraphBuilder() {
		this.parentGraph = null;
	}

	public ObjectGraphBuilder(@Nonnull ObjectGraph parentGraph) {
		this.parentGraph = parentGraph;
	}

	public ObjectGraphBuilder module(Object module) {
		modules.add(module);
		return this;
	}

	public ObjectGraph build() {
		if (modules.isEmpty()) {
			throw new IllegalStateException("no modules specified in ObjectGraphBuilder");
		}

		// collect ObjectGraphNode instances for each module (method)
		List<ObjectGraphNode> nodes = new ArrayList<>();
		for (Object module : modules) {
			int oldSize = nodes.size();
			collectObjectGraphNodes(nodes, module);

			if (oldSize == nodes.size()) {
				throw new IllegalArgumentException("Module " + module.getClass().getName() + " has no public methods annotated with @Provides");
			}
		}

		// No need for concurrent writes: this HashMap is used as read-only after this point
		@SuppressWarnings("PMD.UseConcurrentHashMap")
		Map<InjectSignature, ObjectGraphNode> nodeMap = new HashMap<>(nodes.size());
		for (ObjectGraphNode node : nodes) {
			nodeMap.put(node.getInjectSignature(), node);
		}

		InjectSignatureCache injectSignatureCache = parentGraph != null ? parentGraph.getInjectSignatureCache() : new InjectSignatureCache();

		return new ObjectGraph(parentGraph, nodeMap, injectSignatureCache);
	}

	private static void collectObjectGraphNodes(List<ObjectGraphNode> objectGraphNodes, Object module) {
		for (Method method : module.getClass().getMethods()) {
			// find a matching method
			if (!method.isAnnotationPresent(Provides.class)) {
				continue;
			}
			// create key
			Nullability nullability = Nullability.create(method);
			Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, method);
			InjectSignature injectSignature = new InjectSignature(method.getReturnType(), nullability, qualifierAnnotation);
			objectGraphNodes.add(new ObjectGraphNode(injectSignature, module, method));
		}
	}
}
