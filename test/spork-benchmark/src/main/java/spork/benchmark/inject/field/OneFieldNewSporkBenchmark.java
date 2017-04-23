package spork.benchmark.inject.field;

import javax.inject.Inject;

import spork.SporkInstance;
import spork.benchmark.Benchmark;
import spork.inject.Provides;
import spork.inject.internal.InjectFieldBinder;
import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

public class OneFieldNewSporkBenchmark extends Benchmark {
	private final TestObject testObject;

	public OneFieldNewSporkBenchmark() {
		ObjectGraph graph = new ObjectGraphBuilder()
				.module(new Module())
				.build();

		SporkInstance spork = new SporkInstance();
		spork.register(new InjectFieldBinder());

		testObject = new TestObject(spork, graph);
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
		testObject.inject();
		return 1L;
	}
}