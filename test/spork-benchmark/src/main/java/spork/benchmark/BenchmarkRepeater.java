package spork.benchmark;

import java.util.Locale;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class BenchmarkRepeater implements Runnable {
	private final BenchmarkFactory benchmarkFactory;
	private final BenchmarkResult[] benchmarkResults;

	public BenchmarkRepeater(BenchmarkFactory benchmarkFactory, int runCount) {
		this.benchmarkFactory = benchmarkFactory;
		this.benchmarkResults = new BenchmarkResult[runCount];
	}

	@Override
	public void run() {
		for (int i = 0; i < benchmarkResults.length; ++i) {
			Benchmark benchmark = benchmarkFactory.createBenchmark();
			benchmark.run();

			BenchmarkResult results = benchmark.getBenchmarkResult();
			benchmarkResults[i] = results;
		}
	}

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public BenchmarkResult[] getBenchmarkResults() {
		return benchmarkResults;
	}

	public BenchmarkResult calculateAverageBenchmarkResult() {
		double workCount = 0D;
		double workDuration = 0D;

		for (BenchmarkResult result : benchmarkResults) {
			workCount += result.getWorkCount();
			workDuration += result.getWorkDuration();
		}

		workCount /= (double) benchmarkResults.length;
		workDuration /= (double) benchmarkResults.length;

		return new BenchmarkResult(benchmarkResults[0].getBenchmarkClass(), (long) workDuration, (long) workCount);
	}

	public void printResultOverview() {
		BenchmarkResult result = calculateAverageBenchmarkResult();
		double workDurationMs = result.getWorkDuration() / 1000000d;
		double average = workDurationMs / (double) result.getWorkCount();
		System.out.println("Ran benchmark \"" + benchmarkFactory.createBenchmark().getClass().getSimpleName() + "\" "
				+ Integer.toString(benchmarkResults.length) + " times with "
				+ Long.toString(result.getWorkCount()) + " work items per benchmark and on average "
				+ String.format(Locale.getDefault(), "%.3fms", average) + " per work item");
	}

	public void printResultDetails() {
		System.out.println("Individual results:");
		for (BenchmarkResult result : benchmarkResults) {
			System.out.println("\t" + result.toString());
		}
		System.out.println();
	}
}
