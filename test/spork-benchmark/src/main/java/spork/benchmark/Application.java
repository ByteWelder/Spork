package spork.benchmark;

import spork.Spork;
import spork.benchmark.core.CoreBenchmark;
import spork.benchmark.core.FieldAnnotationBinder;
import spork.benchmark.inject.InjectMultiFieldBenchmark;
import spork.benchmark.inject.InjectSingleFieldBenchmark;
import spork.inject.internal.InjectFieldBinder;

public final class Application {

	private Application() {
	}

	public static void main(String[] args) {
		runCoreBenchmarks();
		runInjectSingleFieldBenchmarks();
		runInjectMultiFieldBenchmarks();
	}

	private static void runCoreBenchmarks() {
		final Spork spork = new Spork();

		spork.getBinderRegistry().register(new FieldAnnotationBinder());

		BenchmarkRepeater injectFirst = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new CoreBenchmark(spork, 1);
			}
		}, 1);

		injectFirst.run();
		injectFirst.printResultOverview();

		BenchmarkRepeater injectOne = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new CoreBenchmark(spork, 1);
			}
		}, 1000);

		injectOne.run();
		injectOne.printResultOverview();

		BenchmarkRepeater injectMany = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new CoreBenchmark(spork, 1000);
			}
		}, 10);

		injectMany.run();
		injectMany.printResultOverview();
	}

	private static void runInjectSingleFieldBenchmarks() {
		final Spork spork = new Spork();
		spork.getBinderRegistry().register(new InjectFieldBinder());

		BenchmarkRepeater injectFirst = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectSingleFieldBenchmark(spork, 1);
			}
		}, 1);

		injectFirst.run();
		injectFirst.printResultOverview();

		BenchmarkRepeater injectOne = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectSingleFieldBenchmark(spork, 1);
			}
		}, 1000);

		injectOne.run();
		injectOne.printResultOverview();

		BenchmarkRepeater injectMany = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectSingleFieldBenchmark(spork, 1000);
			}
		}, 10);

		injectMany.run();
		injectMany.printResultOverview();
	}

	private static void runInjectMultiFieldBenchmarks() {
		final Spork spork = new Spork();
		spork.getBinderRegistry().register(new InjectFieldBinder());

		BenchmarkRepeater injectFirst = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectMultiFieldBenchmark(spork, 1);
			}
		}, 1);

		injectFirst.run();
		injectFirst.printResultOverview();

		BenchmarkRepeater injectOne = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectMultiFieldBenchmark(spork, 1);
			}
		}, 1000);

		injectOne.run();
		injectOne.printResultOverview();

		BenchmarkRepeater injectMany = new BenchmarkRepeater(new BenchmarkFactory() {
			@Override
			public Benchmark createBenchmark() {
				return new InjectMultiFieldBenchmark(spork, 1000);
			}
		}, 10);

		injectMany.run();
		injectMany.printResultOverview();
	}
}
