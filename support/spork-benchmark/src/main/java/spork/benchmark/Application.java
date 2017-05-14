package spork.benchmark;

import spork.benchmark.core.CoreBenchmarkFactories;
import spork.benchmark.inject.complex.ComplexBenchmarkFactories;
import spork.benchmark.inject.field.FieldBenchmarkFactories;
import spork.benchmark.inject.method.MethodBenchmarkFactories;

public final class Application {
	private static final int REPEAT_COUNT = 100;

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
		// Inject a single instance once with a clean Spork instance.
		// This benchmark shows the initial injection overhead of a cold-cache Spork instance.
		BenchmarkFactory oneFieldNewSporkBenchmark = CoreBenchmarkFactories.forOneFieldNewSpork();
		BenchmarkRepeater oneFieldNewSporkRepeater = new BenchmarkRepeater(oneFieldNewSporkBenchmark, REPEAT_COUNT);
		oneFieldNewSporkRepeater.run();
		oneFieldNewSporkRepeater.printResultOverview();

		// Do 1000 runs that inject 1 field with a shared spork instance
		// Then repeat it 10 times.
		// This tests how well injecting a single fields work in a situation where Spork has
		// cached all the necessary data.
		BenchmarkFactory oneFieldSharedSporkBenchmark = CoreBenchmarkFactories.forOneFieldSharedSpork(REPEAT_COUNT);
		BenchmarkRepeater oneFieldSharedSporkRepeater = new BenchmarkRepeater(oneFieldSharedSporkBenchmark, 1);
		oneFieldSharedSporkRepeater.run();
		oneFieldSharedSporkRepeater.printResultOverview();

		// Do 1000 runs that inject 5 fields with a shared spork instance
		// Then repeat it 10 times.
		// This tests how well injecting multiple fields work in a situation where Spork has
		// cached all the necessary data.
		BenchmarkFactory fiveFieldSharedSporkBenchmark = CoreBenchmarkFactories.forFiveFieldSharedSpork(REPEAT_COUNT);
		BenchmarkRepeater fiveFieldSharedSporkRepeater = new BenchmarkRepeater(fiveFieldSharedSporkBenchmark, 1);
		fiveFieldSharedSporkRepeater.run();
		fiveFieldSharedSporkRepeater.printResultOverview();
	}

	/**
	 * This benchmarks tests injecting a single field.
	 */
	private static void runInjectFieldBenchmarks() {
		// Inject a single field once with a clean Spork instance.
		// This benchmark shows the initial injection overhead of a cold-cache Spork instance.
		BenchmarkFactory oneFieldNewSporkBenchmark = FieldBenchmarkFactories.forOneFieldNewSporkBenchmark();
		BenchmarkRepeater oneFieldNewSporkRepeater = new BenchmarkRepeater(oneFieldNewSporkBenchmark, REPEAT_COUNT);
		oneFieldNewSporkRepeater.run();
		oneFieldNewSporkRepeater.printResultOverview();

		// Inject a single field once with a shared Spork instance
		// This benchmark shows the average injection time with a warm-cache
		BenchmarkFactory oneFieldSharedSporkBenchmark = FieldBenchmarkFactories.forOneFieldSharedSporkBenchmark(REPEAT_COUNT);
		BenchmarkRepeater oneFieldSharedSporkRepeater = new BenchmarkRepeater(oneFieldSharedSporkBenchmark, 1);
		oneFieldSharedSporkRepeater.run();
		oneFieldSharedSporkRepeater.printResultOverview();

		// Inject five fields with a sharked Spork instance
		// This benchmark shows the average injection time of multiple fields with a warm-cache
		BenchmarkFactory fiveFieldBenchmark = FieldBenchmarkFactories.forFiveFieldSharedSporkBenchmark(REPEAT_COUNT);
		BenchmarkRepeater fiveFieldRepeater = new BenchmarkRepeater(fiveFieldBenchmark, 1);
		fiveFieldRepeater.run();
		fiveFieldRepeater.printResultOverview();
	}

	/**
	 * This benchmarks tests injecting a single method.
	 */
	private static void runInjectMethodBenchmarks() {
		// Inject a single method once with a clean Spork instance.
		// This benchmark shows the initial injection overhead of a cold-cache Spork instance.
		BenchmarkFactory oneMethodNewSporkBenchmark = MethodBenchmarkFactories.forOneMethodNewSporkBenchmark();
		BenchmarkRepeater oneMethodNewSporkRepeater = new BenchmarkRepeater(oneMethodNewSporkBenchmark, REPEAT_COUNT);
		oneMethodNewSporkRepeater.run();
		oneMethodNewSporkRepeater.printResultOverview();

		// Inject a single method once with a shared Spork instance
		// This benchmark shows the average injection time with a warm-cache
		BenchmarkFactory oneMethodSharedSporkBenchmark = MethodBenchmarkFactories.forOneMethodSharedSporkBenchmark(REPEAT_COUNT);
		BenchmarkRepeater oneMethodSharedSporkRepeater = new BenchmarkRepeater(oneMethodSharedSporkBenchmark, 1);
		oneMethodSharedSporkRepeater.run();
		oneMethodSharedSporkRepeater.printResultOverview();

		// Inject five methods with a sharked Spork instance
		// This benchmark shows the average injection time of multiple methods with a warm-cache
		BenchmarkFactory fiveMethodBenchmark = MethodBenchmarkFactories.forFiveMethodSharedSporkBenchmark(REPEAT_COUNT);
		BenchmarkRepeater fiveMethodRepeater = new BenchmarkRepeater(fiveMethodBenchmark, 1);
		fiveMethodRepeater.run();
		fiveMethodRepeater.printResultOverview();
	}

	/**
	 * This method runs complex mixed benchmarks that are supposed to represent
	 * more realistic injection scenarios.
	 */
	private static void runInjectComplexBenchmarks() {
		BenchmarkFactory complexBenchmark = ComplexBenchmarkFactories.forOneFieldNewSporkBenchmark(REPEAT_COUNT);
		BenchmarkRepeater complexBenchmarkRepeater = new BenchmarkRepeater(complexBenchmark, 1);
		complexBenchmarkRepeater.run();
		complexBenchmarkRepeater.printResultOverview();
	}
}
