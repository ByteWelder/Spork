package spork.benchmark.inject.complex;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.InjectMethodBinder;

public final class InjectComplex extends Benchmark {
	private final TestObject[] testObjects;

	public InjectComplex(int iterationCount) {
		SporkInstance spork = new SporkInstance();
		spork.register(new InjectFieldBinder());
		spork.register(new InjectMethodBinder());

		// Root graph with RootModule
		ObjectGraph rootGraph = ObjectGraphs.builder()
				.module(new RootModule())
				.build();

		// Child graph with Root graph as parent and ChildModule
		ObjectGraph childGraph = ObjectGraphs.builder(rootGraph)
				.module(new ChildModule())
				.build();

		// Warm up the cache
		TestObject warmUpObject = new TestObject(spork, childGraph);
		warmUpObject.inject();

		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork, childGraph);
		}
	}

	private static final class Service {
	}

	public static final class RootModule {
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

	public static final class ChildModule {
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