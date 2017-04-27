package spork.android.example;

import spork.inject.ObjectGraph;

/**
 * Interface to access ObjectGraph from Application, Activity, Fragment, ...
 */
public interface ObjectGraphProvider {
	ObjectGraph getObjectGraph();
}
