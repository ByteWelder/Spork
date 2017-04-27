package spork.inject;

import java.lang.annotation.Annotation;

/**
 * Builder pattern implementation for {link ObjectGraph}.
 */
public interface ObjectGraphBuilder {

	/**
	 * Sets the scope that the ObjectGraph is hosting.
	 * Should only be called once.
	 */
	ObjectGraphBuilder scope(Class<? extends Annotation> scope);

	/**
	 * Add a module to the ObjectGraph.
	 * Can be called multiple times.
	 */
	ObjectGraphBuilder module(Object module);

	/**
	 * Build the ObjectGraph.
	 */
	ObjectGraph build();
}
