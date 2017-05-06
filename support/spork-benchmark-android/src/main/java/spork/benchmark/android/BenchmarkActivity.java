package spork.benchmark.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import spork.Spork;
import spork.android.BindClick;
import spork.android.BindLayout;
import spork.android.BindView;

@BindLayout(R.layout.activity_benchmark)
public class BenchmarkActivity extends Activity {
	private final Handler handler = new Handler();

	@BindView(R.id.startButton)
	private Button startButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Spork.bind(this);
	}

	@BindClick(R.id.startButton)
	protected void onClickStart() {
		startBenchmark();
	}

	private void startBenchmark() {
		showBusy(true);

		// Run with delay because otherwise the view is not updated
		// Run on main thread because background threads get less performance/priority
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				spork.benchmark.Application.main(new String[]{});

				if (isFinishing()) {
					return;
				}

				showBusy(false);
			}
		}, 100);
	}

	private void showBusy(boolean busy) {
		startButton.setEnabled(!busy);
		startButton.setText(busy ? R.string.busy : R.string.start);
	}
}
