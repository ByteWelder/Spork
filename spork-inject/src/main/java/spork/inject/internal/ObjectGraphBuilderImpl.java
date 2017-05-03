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

import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphBuilder;
import spork.inject.Provides;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;
import spork.inject.internal.reflection.InjectSignature;
import spork.inject.internal.reflection.InjectSignatureFieldCache;
import spork.inject.internal.reflection.InjectSignatureMethodCache;
import spork.inject.internal.reflection.QualifierCache;
import spork.inject.internal.reflection.ReflectionCache;

public class ObjectGraphBuilderImpl implements ObjectGraphBuilder {
	@Nullable
	private final ObjectGraphImpl parentGraph;
	@Nullable
	private Class<? extends Annotation> scope;
	private final List<Object> modules = new ArrayList<>(4);

	public ObjectGraphBuilderImpl() {
		this.parentGraph = null;
	}

	public ObjectGraphBuilderImpl(@Nonnull ObjectGraphImpl parentGraph) {
		this.parentGraph = parentGraph;
	}

	// region Append methods

	@Override
	public ObjectGraphBuilder scope(Class<? extends Annotation> scope) {
		if (this.scope != null) {
			throw new IllegalStateException("Scope was already set");
		}

		this.scope = scope;

		return this;
	}

	@Override
	public ObjectGraphBuilder module(Object module) {
		modules.add(module);
		return this;
	}

	// endregion

	// region Finalization methods

	@Override
	public ObjectGraph build() {
		validate();

		ReflectionCache reflectionCache = buildInjectSignatureProvider();

		// collect ObjectGraphNode instances for each module (method)
		List<ObjectGraphNode> nodes = collectObjectGraphNodes(reflectionCache, modules);

		// Index all ObjectGraphNode instances
		Map<InjectSignature, ObjectGraphNode> nodeMap = createNodeMap(nodes);

		// The root graph is always the Singleton-scoped one
		Class<? extends Annotation> effectiveScope = (parentGraph == null) ? Singleton.class : scope;

		return new ObjectGraphImpl(parentGraph, nodeMap, reflectionCache, effectiveScope);
	}

	private ReflectionCache buildInjectSignatureProvider() {
		// If possible, inherit the ReflectionCache to improve performance
		if (parentGraph == null) {
			QualifierCache qualifierCache = new QualifierCache();
			InjectSignatureFieldCache fieldProvider = new InjectSignatureFieldCache(qualifierCache);
			InjectSignatureMethodCache methodProvider = new InjectSignatureMethodCache(qualifierCache);
			return new ReflectionCache(qualifierCache, fieldProvider, methodProvider);
		} else {
			return parentGraph.getReflectionCache();
		}
	}

	private void validate() {
		if (modules.isEmpty()) {
			throw new IllegalStateException("No modules specified in ObjectGraphBuilder");
		}

		if (scope != null && parentGraph == null) {
			// The root ObjectGraph is always the Singleton-scoped one
			throw new IllegalStateException("Scope annotation can only be used when a parent ObjectGraph is specified");
		}
	}

	// endregion

	// region Private static helper methods

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
	private static List<ObjectGraphNode> collectObjectGraphNodes(ReflectionCache reflectionCache, List<Object> modules) {
		List<ObjectGraphNode> nodes = new ArrayList<>();
		for (Object module : modules) {
			int oldSize = nodes.size();
			collectObjectGraphNodes(reflectionCache, nodes, module);

			if (oldSize == nodes.size()) {
				throw new IllegalArgumentException("Module " + module.getClass().getName() + " has no public methods annotated with @Provides");
			}
		}
		return nodes;
	}

	/**
	 * Collect the ObjectGraphNode instances for a specific module.
	 */
	private static void collectObjectGraphNodes(ReflectionCache reflectionCache, List<ObjectGraphNode> objectGraphNodes, Object module) {
		for (Method method : module.getClass().getMethods()) {
			// find a matching method
			if (!method.isAnnotationPresent(Provides.class)) {
				continue;
			}
			// getQualifier key
			Nullability nullability = Nullability.create(method);
			Annotation qualifierAnnotation = Annotations.findAnnotationAnnotatedWith(Qualifier.class, method);
			String qualifier = qualifierAnnotation != null
					? reflectionCache.getQualifier(qualifierAnnotation)
					: null;
			InjectSignature injectSignature = new InjectSignature(method.getReturnType(), nullability, qualifier);
			objectGraphNodes.add(new ObjectGraphNode(injectSignature, module, method));
		}
	}

	// endregion
}
