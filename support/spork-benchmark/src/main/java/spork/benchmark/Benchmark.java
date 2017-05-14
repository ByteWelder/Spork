package spork.benchmark;

public abstract class Benchmark {

	public final BenchmarkResult run() {
		// Invoke garbage collection to clean up any memory before running a test.
		// This should reduce the chances of GC happening during our test.
		System.gc();

		long startTime = System.nanoTime();
		long workCount = doWork();
		long endTime = System.nanoTime();

		return new BenchmarkResult(getClass(), endTime - startTime, workCount);
	}

	protected abstract long doWork();
}
