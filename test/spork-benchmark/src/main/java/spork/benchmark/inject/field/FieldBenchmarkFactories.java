package spork.benchmark.inject.field;

import spork.benchmark.Benchmark;
import spork.benchmark.BenchmarkFactory;

public final class FieldBenchmarkFactories {

	private FieldBenchmarkFactories() {
	}

	public static BenchmarkFactory forOneFieldNewSporkBenchmark() {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneFieldNewSporkBenchmark();
			}
		};
	}

	public static BenchmarkFactory forOneFieldSharedSporkBenchmark(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneFieldSharedSporkBenchmark(repeat);
			}
		};
	}

	public static BenchmarkFactory forFiveFieldSharedSporkBenchmark(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FiveFieldSharedSporkBenchmark(repeat);
			}
		};
	}
}
