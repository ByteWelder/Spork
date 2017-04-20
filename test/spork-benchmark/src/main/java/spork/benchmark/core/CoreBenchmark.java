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

		@TestAnnotation
		Object a;

		@TestAnnotation
		Object b;

		@TestAnnotation
		Object c;

		@TestAnnotation
		Object d;

		@TestAnnotation
		Object e;

		public void bind() {
			spork.bind(this);
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
