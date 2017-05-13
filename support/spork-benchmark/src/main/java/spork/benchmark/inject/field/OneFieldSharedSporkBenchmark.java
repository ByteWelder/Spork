package spork.benchmark.inject.field;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;
import spork.inject.internal.InjectFieldBinder;

final class OneFieldSharedSporkBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	OneFieldSharedSporkBenchmark(int iterationCount) {
		ObjectGraph graph = ObjectGraphs.builder()
				.module(new Module())
				.build();

		SporkInstance spork = new SporkInstance();
		spork.register(new InjectFieldBinder());

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
	}

	public static final class Module {
		@Provides
		public Object provideObject() {
			return "test";
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
