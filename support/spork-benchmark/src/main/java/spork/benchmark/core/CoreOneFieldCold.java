package spork.benchmark.core;

import spork.SporkInstance;
import spork.benchmark.Benchmark;

public final class CoreOneFieldCold extends Benchmark {
	private final TestObject[] testObjects;

	public CoreOneFieldCold(int iterationCount) {
		testObjects = new TestObject[iterationCount];
		for (int i = 0; i < testObjects.length; ++i) {
			testObjects[i] = new TestObject();
		}
	}

	public static final class TestObject {
		private final SporkInstance spork;

		public TestObject() {
			this.spork = new SporkInstance();
			this.spork.register(new TestBinder());
		}

		@TestAnnotation
		Object a;

		void bind() {
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