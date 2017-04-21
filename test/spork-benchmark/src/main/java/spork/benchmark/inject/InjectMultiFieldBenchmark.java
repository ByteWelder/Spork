package spork.benchmark.inject;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.Provides;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

public class InjectMultiFieldBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	public InjectMultiFieldBenchmark(SporkInstance spork, int iterationCount) {
		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
	}

	public InjectMultiFieldBenchmark(SporkInstance spork) {
		this(spork, 1000);
	}

	public static final class Module {
		@Provides
		public Object provideObject() {
			return "Test";
		}
	}

	public static final class TestObject {
		private final SporkInstance spork;
		private final ObjectGraph objectGraph;

		public TestObject(SporkInstance spork, ObjectGraph objectGraph) {
			this.spork = spork;
			this.objectGraph = objectGraph;
		}

		@Inject
		Object a;

		@Inject
		Object b;

		@Inject
		Object c;

		@Inject
		Object d;

		@Inject
		Object e;

		public void inject() {
			objectGraph.inject(this, spork);
		}
	}

	@Override
	protected long doWork() {
		for (TestObject testObject : testObjects) {
			testObject.inject();
		}

		return testObjects.length;
	}
}
