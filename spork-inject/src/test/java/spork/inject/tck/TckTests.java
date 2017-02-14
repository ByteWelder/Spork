package spork.inject.tck;

import junit.framework.TestCase;

import org.atinject.tck.Tck;

import spork.Spork;
import spork.inject.internal.objectgraph.ObjectGraph;

public class TckTests extends TestCase {

	public static junit.framework.Test suite() {
		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new CarModule())
				.module(new CarComponentsModule())
				.build();

		CarHolder carHolder = new CarHolder();

		Spork.bind(carHolder, graph);
		Spork.bind(carHolder.getCar(), graph);

		return Tck.testsFor(carHolder.getCar(), false, false);
	}
}
