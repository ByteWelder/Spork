package spork.benchmark.android;

import android.app.Activity;
import android.os.Bundle;

import spork.benchmark.Benchmark;
import spork.benchmark.BenchmarkFactory;
import spork.benchmark.BenchmarkRepeater;
import spork.benchmark.benchmarks.FieldBinder1Benchmark;
import spork.benchmark.benchmarks.FieldBinder2Benchmark;

public class BenchmarkActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_benchmark);
    }

	@Override
	protected void onResume() {
		super.onResume();

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
