package spork.benchmark.inject.complex;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.Provides;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

class ComplexInjectBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	ComplexInjectBenchmark(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new InjectFieldBinder());
		spork.register(new InjectMethodBinder());

		// Root graph with RootModule
		ObjectGraph rootGraph = new ObjectGraphBuilder()
				.module(new RootModule())
				.build();

		// Child graph with Root graph as parent and ChildModule
		ObjectGraph childGraph = new ObjectGraphBuilder(rootGraph)
				.module(new ChildModule())
				.build();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, childGraph);
		}
	}

	private static final class Service {
	}

	private static final class RootModule {
		@Provides
		@Named("root")
		public String provideString() {
			return "rootString";
		}

		@Provides
		@Singleton
		public Object provideSingleton() {
			return "singletonObject";
		}
	}

	private static final class ChildModule {
		@Provides
		@Named("child")
		public String provideString() {
			return "childString";
		}

		@Provides
		public Service childService() {
			return new Service();
		}
	}

	private static final class TestObject {
		private final SporkInstance spork;
		private final ObjectGraph objectGraph;

		public TestObject(SporkInstance spork, ObjectGraph objectGraph) {
			this.spork = spork;
			this.objectGraph = objectGraph;
		}

		@Inject
		@Named("root")
		String rootString;

		@Inject
		@Named("child")
		String childString;

		@Inject
		Object singletonObject;

		@Inject
		private void initialize(Service service) {
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