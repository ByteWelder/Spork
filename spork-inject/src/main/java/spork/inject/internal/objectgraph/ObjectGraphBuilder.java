package spork.inject.internal.objectgraph;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import spork.inject.internal.objectgraph.modulenode.InstanceCache;
import spork.inject.internal.objectgraph.modulenode.ModuleNode;

public class ObjectGraphBuilder {
	@Nullable
	private final ObjectGraph parentGraph;
	private final ArrayList<ObjectGraphNode> nodeList = new ArrayList<>(2);
	private final InstanceCache instanceCache = new InstanceCache();

	public ObjectGraphBuilder() {
		this.parentGraph = null;
	}

	public ObjectGraphBuilder(@Nonnull ObjectGraph parentGraph) {
		this.parentGraph = parentGraph;
	}

	public ObjectGraphBuilder module(Object module) {
		nodeList.add(new ModuleNode(module, instanceCache));
		return this;
	}

	public ObjectGraph build() {
		ObjectGraphNode[] nodeArray = nodeList.toArray(new ObjectGraphNode[nodeList.size()]);
		return new ObjectGraph(parentGraph, nodeArray);
	}
}
