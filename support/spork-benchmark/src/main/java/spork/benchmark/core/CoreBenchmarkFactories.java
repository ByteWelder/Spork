package spork.benchmark.core;

import spork.benchmark.Benchmark;
import spork.benchmark.BenchmarkFactory;

public final class CoreBenchmarkFactories {

	private CoreBenchmarkFactories() {
	}

	public static BenchmarkFactory forOneFieldNewSpork() {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneFieldNewSporkBenchmark();
			}
		};
	}

	public static BenchmarkFactory forOneFieldSharedSpork(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new OneFieldSharedSporkInstance(repeat);
			}
		};
	}

	public static BenchmarkFactory forFiveFieldSharedSpork(final int repeat) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FiveFieldSharedSporkBenchmark(repeat);
			}
		};
	}
}
