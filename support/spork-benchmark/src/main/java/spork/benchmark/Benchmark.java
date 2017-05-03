package spork.benchmark;

import javax.annotation.Nullable;

public abstract class Benchmark implements Runnable {

	@Nullable
	private BenchmarkResult benchmarkResult;

	@Override
	public void run() {
		long startTime = System.nanoTime();
		long workCount = doWork();
		long endTime = System.nanoTime();

		benchmarkResult = new BenchmarkResult(getClass(), endTime - startTime, workCount);
	}

	protected abstract long doWork();

	public final BenchmarkResult getBenchmarkResult() {
		if (benchmarkResult == null) {
			throw new IllegalStateException("Results are only available after running the BenchmarkTest");
		}

		return benchmarkResult;
	}
}
