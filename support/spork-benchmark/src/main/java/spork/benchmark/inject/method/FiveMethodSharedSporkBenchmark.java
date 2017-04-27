package spork.benchmark.inject.method;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.Provides;
import spork.inject.internal.InjectMethodBinder;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

class FiveMethodSharedSporkBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	FiveMethodSharedSporkBenchmark(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new InjectMethodBinder());

		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, graph);
		}
	}

	public static final class Module {

		@Provides
		public int provideInt() {
			return 1;
		}

		@Provides
		public long provideLong() {
			return 1L;
		}

		@Provides
		public boolean provideBoolean() {
			return true;
		}

		@Provides
		public float provideFloat() {
			return 1.0f;
		}

		@Provides
		public String provideString() {
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
		private void onInjectInt(int value) {
		}

		@Inject
		private void onInjectLong(long value) {
		}

		@Inject
		private void onInjectBoolean(boolean value) {
		}

		@Inject
		private void onInjectFloat(float value) {
		}

		@Inject
		private void onInjectString(String text) {
		}

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
