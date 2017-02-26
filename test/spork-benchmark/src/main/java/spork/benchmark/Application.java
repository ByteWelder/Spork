package spork.benchmark;

import spork.benchmark.benchmarks.FieldBinder1Benchmark;
import spork.benchmark.benchmarks.FieldBinder2Benchmark;
import spork.benchmark.binders.FieldBinder1;
import spork.benchmark.binders.FieldBinder2;
import spork.Spork;

public final class Application {

	private Application() {
	}

	public static void main(String[] args) {
		Spork.sharedInstance().getBinderRegistry().register(new FieldBinder1());
		Spork.sharedInstance().getBinderRegistry().register(new FieldBinder2());

		BenchmarkRepeater repeater1 = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FieldBinder1Benchmark(1);
			}
		}, 10);

		repeater1.run();
		repeater1.printResults();

		BenchmarkRepeater repeater2 = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FieldBinder2Benchmark(1);
			}
		}, 10);

		repeater2.run();
		repeater2.printResults();

		BenchmarkRepeater repeater1Many = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FieldBinder1Benchmark(1000);
			}
		}, 10);

		repeater1Many.run();
		repeater1Many.printResults();

		BenchmarkRepeater repeater2Many = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new FieldBinder2Benchmark(1000);
			}
		}, 10);

		repeater2Many.run();
		repeater2Many.printResults();
	}
}
