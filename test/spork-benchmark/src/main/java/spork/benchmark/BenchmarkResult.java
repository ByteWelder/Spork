package spork.benchmark;

import java.util.Locale;

public final class BenchmarkResult {
	private Class<? extends Benchmark> benchmarkClass;
	private final long workDuration;
	private final long workCount;

	public BenchmarkResult(Class<? extends Benchmark> benchmarkClass, long workDuration, long workCount) {
		this.benchmarkClass = benchmarkClass;
		this.workDuration = workDuration;
		this.workCount = workCount;
	}

	/**
	 * @return the duration of the Benchmark
	 */
	public long getWorkDuration() {
		return workDuration;
	}

	/**
	 * @return the amount of work items executed in a Benchmark run
	 */
	public long getWorkCount() {
		return workCount;
	}

	public Class<? extends Benchmark> getBenchmarkClass() {
		return benchmarkClass;
	}

	@Override
	public String toString() {
		double duration = workDuration / 1000000d;

		return String.format(Locale.getDefault(), "[%s took %.3fms with %d tasks @ %.3fms/task]",
				benchmarkClass.getSimpleName(),
				duration,
				workCount,
				duration / (double)workCount);
	}
}
