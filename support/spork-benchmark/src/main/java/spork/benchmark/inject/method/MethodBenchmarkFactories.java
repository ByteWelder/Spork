package spork.benchmark.inject.method;

import spork.benchmark.Benchmark;
import spork.benchmark.BenchmarkFactory;

public final class MethodBenchmarkFactories {

	private MethodBenchmarkFactories() {
	}

	public static BenchmarkFactory forOneMethodNewSporkBenchmark() {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneMethodNewSporkBenchmark();
			}
		};
	}

	public static BenchmarkFactory forOneMethodSharedSporkBenchmark(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneMethodSharedSporkBenchmark(repeat);
			}
		};
	}

	public static BenchmarkFactory forFiveMethodSharedSporkBenchmark(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FiveMethodSharedSporkBenchmark(repeat);
			}
		};
	}
}
