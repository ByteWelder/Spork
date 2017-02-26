package spork.benchmark.benchmarks;

import spork.benchmark.Benchmark;
import spork.benchmark.binders.BindTest2;
import spork.Spork;

public class FieldBinder2Benchmark extends Benchmark {
	private final TestObject[] testObjects;

	public FieldBinder2Benchmark(int iterationCount) {
		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject();
		}
	}

	public FieldBinder2Benchmark() {
		this(1000);
	}

	public static final class TestObject {
		@BindTest2
		Object a;

		@BindTest2
		Object b;

		@BindTest2
		Object c;

		@BindTest2
		Object d;

		@BindTest2
		Object e;

		public void bind() {
			Spork.bind(this);
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
