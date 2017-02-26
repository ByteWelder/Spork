package spork.benchmark.benchmarks;

import spork.benchmark.Benchmark;
import spork.benchmark.binders.BindTest1;
import spork.Spork;

public class FieldBinder1Benchmark extends Benchmark {
	private final TestObject[] testObjects;

	public FieldBinder1Benchmark(int iterationCount) {
		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject();
		}
	}

	public FieldBinder1Benchmark() {
		this(1000);
	}

	public static final class TestObject {
		@BindTest1
		Object a;

		@BindTest1
		Object b;

		@BindTest1
		Object c;

		@BindTest1
		Object d;

		@BindTest1
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
