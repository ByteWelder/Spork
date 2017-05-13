package spork.benchmark.core;

import spork.SporkInstance;
import spork.benchmark.Benchmark;

final class OneFieldNewSporkBenchmark extends Benchmark {
	private final TestObject testObject;

	OneFieldNewSporkBenchmark() {
		testObject = new TestObject();
	}

	public static final class TestObject {
		private final SporkInstance spork;

		public TestObject() {
			this.spork = new SporkInstance();
			this.spork.register(new TestBinder());
		}

		@TestAnnotation
		Object a;

		public void bind() {
			spork.bind(this);
		}
	}

	@Override
	protected long doWork() {
		testObject.bind();
		return 1L;
	}
}