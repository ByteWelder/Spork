package spork.android.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import spork.android.BindClick;
import spork.android.BindLayout;
import spork.android.BindView;
import spork.android.example.R;
import spork.android.example.services.SessionService;

import static spork.android.example.ObjectGraphs.objectGraphOf;

@BindLayout(R.layout.activity_welcome)
public class WelcomeActivity extends AppCompatActivity {

	@BindView(R.id.sessionIdTextView)
	private TextView sessionIdTextView;

	@Inject
	private SessionService sessionService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		objectGraphOf(getApplication()).inject(this);

		// Show the session ID in a TextView
		String sessionId = sessionService.getCurentSession().getId();
		String sessionIdText = getString(R.string.session_id, sessionId);
		sessionIdTextView.setText(sessionIdText);
	}

	@BindClick(R.id.logoutButton)
	private void onClickLogout() {
		sessionService.getCurentSession().deactivate();

		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);

		finish();
	}
}
