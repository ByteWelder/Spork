package spork.inject.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Qualifier;

import spork.inject.Provides;
import spork.inject.internal.lang.Annotations;
import spork.inject.internal.lang.Nullability;

public class ObjectGraphBuilder {
	@Nullable
	private final ObjectGraph parentGraph;
	private final ArrayList<Object> modules = new ArrayList<>(2);

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
		List<ObjectGraphNode> objectGraphNodes = new ArrayList<>();

		// collect ObjectGraphNode instances for each module (method)
		for (Object module : modules) {
			 collectObjectGraphNodes(objectGraphNodes, module);
		}

		ObjectGraphNode[] nodeArray = objectGraphNodes.toArray(new ObjectGraphNode[objectGraphNodes.size()]);
		return new ObjectGraph(parentGraph, nodeArray);
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
