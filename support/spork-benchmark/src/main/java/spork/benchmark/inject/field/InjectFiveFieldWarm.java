package spork.benchmark.inject.field;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.ObjectGraph;

public final class InjectFiveFieldWarm extends Benchmark {
	private final TestObject[] testObjects;

	public InjectFiveFieldWarm(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new InjectFieldBinder());

		ObjectGraph graph = ObjectGraphs.builder()
				.module(new Module())
				.build();

		// Warm up the cache
		TestObject warmUpObject = new TestObject(spork, graph);
		warmUpObject.inject();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
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
