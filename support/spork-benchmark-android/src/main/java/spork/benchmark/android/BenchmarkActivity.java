package spork.benchmark.android;

import android.app.Activity;
import android.os.Bundle;

public class BenchmarkActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_benchmark);
	}

	@Override
	protected void onResume() {
		super.onResume();

		spork.benchmark.Application.main(new String[]{});
	}
}
