package spork.benchmark.core;

import spork.Spork;
import spork.benchmark.Benchmark;

public class CoreBenchmark extends Benchmark {
	private final TestObject[] testObjects;

	public CoreBenchmark(Spork spork, int iterationCount) {
		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject(spork);
		}
	}

	public CoreBenchmark(Spork spork) {
		this(spork, 1000);
	}

	public static final class TestObject {
		private final Spork spork;

		public TestObject(Spork spork) {
			this.spork = spork;
		}

		@FieldAnnotation
		Object a;

		@FieldAnnotation
		Object b;

		@FieldAnnotation
		Object c;

		@FieldAnnotation
		Object d;

		@FieldAnnotation
		Object e;

		public void bind() {
			spork.getBinder().bind(this);
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
