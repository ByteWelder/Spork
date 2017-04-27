package spork.android.proguard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import spork.android.BindView;
import spork.android.proguard.services.RestService;
import spork.android.proguard.services.Test;
import spork.inject.ObjectGraph;

public class MainActivity extends Activity {

	@Inject
	private RestService restService;

	@BindView(R.id.textView)
	private TextView textView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		getObjectGraph().inject(this);

		restService.doSomething();
		textView.setText("Hello world!");

		new Test();
	}

	private ObjectGraph getObjectGraph() {
		return ((Application) getApplication()).getObjectGraph();
	}
}
