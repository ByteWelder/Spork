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
import javax.inject.Singleton;

import spork.inject.Provides;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public class ObjectGraphBuilder {
	@Nullable
	private final ObjectGraph parentGraph;
	@Nullable
	private Class<? extends Annotation> scope;
	private final List<Object> modules = new ArrayList<>(4);

	public ObjectGraphBuilder() {
		this.parentGraph = null;
	}

	public ObjectGraphBuilder(@Nonnull ObjectGraph parentGraph) {
		this.parentGraph = parentGraph;
	}

	// region builder methods

	public ObjectGraphBuilder scope(Class<? extends Annotation> scope) {
		if (this.scope != null) {
			throw new IllegalStateException("scope was already set");
		}

		this.scope = scope;

		return this;
	}

	public ObjectGraphBuilder module(Object module) {
		modules.add(module);
		return this;
	}

	public ObjectGraph build() {
		validate();

		// collect ObjectGraphNode instances for each module (method)
		List<ObjectGraphNode> nodes = collectObjectGraphNodes(modules);

		// Index all ObjectGraphNode instances
		Map<InjectSignature, ObjectGraphNode> nodeMap = createNodeMap(nodes);

		// The root graph is always the Singleton-scoped one
		Class<? extends Annotation> effectiveScope = parentGraph == null ? Singleton.class : scope;

		// Inherit the InjectSignatureCache to improve performance
		InjectSignatureCache injectSignatureCache = parentGraph != null ? parentGraph.getInjectSignatureCache() : new InjectSignatureCache();

		return new ObjectGraph(parentGraph, nodeMap, injectSignatureCache, effectiveScope);
	}

	// endregion

	// region private methods

	private void validate() {
		if (modules.isEmpty()) {
			throw new IllegalStateException("No modules specified in ObjectGraphBuilder");
		}

		if (scope != null && parentGraph == null) {
			// The root ObjectGraph is always the Singleton-scoped one
			throw new IllegalStateException("Scope annotation can only be used when a parent ObjectGraph is specified");
		}
	}

	/**
	 * Index the provided ObjectGraphNode instances onto their InjectSignatures.
	 */
	@SuppressWarnings("PMD.UseConcurrentHashMap") // this Map is read-only after creation
	private static Map<InjectSignature, ObjectGraphNode> createNodeMap(List<ObjectGraphNode> nodes) {
		Map<InjectSignature, ObjectGraphNode> nodeMap = new HashMap<>(nodes.size());

		for (ObjectGraphNode node : nodes) {
			nodeMap.put(node.getInjectSignature(), node);
		}

		return nodeMap;
	}

	/**
	 * Collect the ObjectGraphNode instances for all specified modules
	 */
	private static List<ObjectGraphNode> collectObjectGraphNodes(List<Object> modules) {
		List<ObjectGraphNode> nodes = new ArrayList<>();
		for (Object module : modules) {
			int oldSize = nodes.size();
			collectObjectGraphNodes(nodes, module);

			if (oldSize == nodes.size()) {
				throw new IllegalArgumentException("Module " + module.getClass().getName() + " has no public methods annotated with @Provides");
			}
		}
		return nodes;
	}

	/**
	 * Collect the ObjectGraphNode instances for a specific module.
	 */
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

	// endregion
}
