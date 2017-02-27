package spork.benchmark.inject;

import javax.inject.Inject;

import spork.benchmark.Benchmark;
import spork.Spork;
import spork.inject.Provides;
import spork.inject.internal.objectgraph.ObjectGraph;

public class InjectSingleFieldBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	public InjectSingleFieldBenchmark(Spork spork, int iterationCount) {
		ObjectGraph graph = new ObjectGraph.Builder()
				.module(new Module())
				.build();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
	}

	public InjectSingleFieldBenchmark(Spork spork) {
		this(spork, 1000);
	}

	public static final class Module {
		@Provides
		public Object provideObject() {
			return "test";
		}
	}

	public static final class TestObject {
		private final Spork spork;
		private final ObjectGraph objectGraph;

		public TestObject(Spork spork, ObjectGraph objectGraph) {
			this.spork = spork;
			this.objectGraph = objectGraph;
		}

		@Inject
		Object a;

		public void bind() {
			spork.getBinder().bind(this, objectGraph);
		}
	}

	@Override
	protected long doWork() {
		for (TestObject testObject : testObjects) {
			testObject.bind();
		}

		return testObjects.length;
	}
}
