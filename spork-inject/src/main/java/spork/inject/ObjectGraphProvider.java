package spork.inject;

/**
 * Can be used in combination with {@link ObjectGraphs}.objectGraphFrom()
 */
public interface ObjectGraphProvider {
	ObjectGraph getObjectGraph();
}
