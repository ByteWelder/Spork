package spork.benchmark;

import spork.benchmark.core.CoreFiveFieldWarm;
import spork.benchmark.core.CoreOneFieldCold;
import spork.benchmark.core.CoreOneFieldWarm;
import spork.benchmark.inject.complex.InjectComplex;
import spork.benchmark.inject.field.InjectFiveFieldWarm;
import spork.benchmark.inject.field.InjectOneFieldWarm;
import spork.benchmark.inject.method.InjectFiveMethodWarm;
import spork.benchmark.inject.method.InjectOneMethodWarm;

public final class Application {
	private static final int REPEAT_COUNT = 200;

	private Application() {
	}

	public static void main(String[] args) {
		// spork-core
		runCoreBenchmarks();

		// spork-inject
		runInjectFieldBenchmarks();
		runInjectMethodBenchmarks();
		runInjectComplexBenchmarks();
	}

	/**
	 * This Benchmark tests annotation binding speed.
	 */
	private static void runCoreBenchmarks() {
		// This benchmark shows the initial injection overhead of a cold-cache Spork instance.
		BenchmarkResult oneFieldColdResult = new CoreOneFieldCold(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(oneFieldColdResult);

		// This tests how well injecting a single fields work in a situation where Spork has cached all the necessary data.
		BenchmarkResult oneFieldWarmResult = new CoreOneFieldWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(oneFieldWarmResult);

		// This tests how well injecting multiple fields work in a situation where Spork has cached all the necessary data.
		BenchmarkResult fiveFieldWarmResult = new CoreFiveFieldWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(fiveFieldWarmResult);
	}

	/**
	 * This benchmarks tests injecting a single field.
	 */
	private static void runInjectFieldBenchmarks() {
		// This benchmark shows the average single injection time with a warm-cache
		BenchmarkResult oneFieldWarmResult = new InjectOneFieldWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(oneFieldWarmResult);

		// This benchmark shows the average injection time of multiple fields with a warm-cache
		BenchmarkResult fiveFieldWarmResult = new InjectFiveFieldWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(fiveFieldWarmResult);
	}

	/**
	 * This benchmarks tests injecting a single method.
	 */
	private static void runInjectMethodBenchmarks() {
		// This benchmark shows the average single injection time with a warm-cache
		BenchmarkResult oneMethodWarmResult = new InjectOneMethodWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(oneMethodWarmResult);

		// This benchmark shows the average injection time of multiple methods with a warm-cache
		BenchmarkResult fiveMethodWarmResult = new InjectFiveMethodWarm(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(fiveMethodWarmResult);
	}

	/**
	 * This method runs complex mixed benchmarks that are supposed to represent
	 * more realistic injection scenarios.
	 */
	private static void runInjectComplexBenchmarks() {
		BenchmarkResult result = new InjectComplex(REPEAT_COUNT).run();
		BenchmarkResultPrinter.print(result);
	}
}
