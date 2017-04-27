package spork.android.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import spork.android.example.services.Session;
import spork.android.example.services.SessionService;

import static spork.android.example.ObjectGraphs.objectGraphOf;

public class LaunchActivity extends AppCompatActivity {

	@Inject
	private SessionService sessionService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the Application's ObjectGraph
		objectGraphOf(getApplication())
				.inject(this);

		// Forward to the right Activity (LoginActivity or WelcomeActivity)
		// depending on whether we're logged in or not
		Intent intent = getForwardingIntent();
		startActivity(intent);
		finish();
	}

	private Intent getForwardingIntent() {
		Session session = sessionService.getCurentSession();
		if (session.isActive()) {
			return new Intent(this, WelcomeActivity.class);
		} else {
			return new Intent(this, LoginActivity.class);
		}
	}
}
