package spork.benchmark.inject.complex;

import spork.benchmark.Benchmark;
import spork.benchmark.BenchmarkFactory;

public final class ComplexBenchmarkFactories {

	private ComplexBenchmarkFactories() {
	}

	public static BenchmarkFactory forOneFieldNewSporkBenchmark(final int iterations) {
		return new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new ComplexInjectBenchmark(iterations);
			}
		};
	}
}
