package spork.benchmark;

import java.util.Locale;

public final class BenchmarkResultPrinter {

	private BenchmarkResultPrinter() {
	}

	public static void print(BenchmarkResult result) {
		double workDurationMs = result.getWorkDuration() / 1000000d;
		double average = workDurationMs / (double) result.getWorkCount();
		System.out.println("Ran benchmark " + result.getBenchmarkClass().getName() + ":\n"
				+ "\t- work items:      " + Long.toString(result.getWorkCount()) + "\n"
				+ "\t- avg. time/item:  " + String.format(Locale.getDefault(), "%.3f ms", average));
	}
}