package example.spork.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import javax.inject.Inject;

import spork.android.BindClick;
import spork.android.BindLayout;
import example.spork.R;
import example.spork.concurrent.Callback;
import example.spork.services.Session;
import example.spork.services.SessionService;

import static spork.inject.ObjectGraphs.objectGraphFrom;

@BindLayout(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
	private ProgressDialog loginProgressDialog;

	@Inject
	private SessionService sessionService;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		objectGraphFrom(getApplication()).inject(this);

		loginProgressDialog = new ProgressDialog(this);
		loginProgressDialog.setMessage(getString(R.string.login_progress));
	}

	@Override
	protected void onDestroy() {
		if (loginProgressDialog.isShowing()) {
			loginProgressDialog.dismiss();
		}

		super.onDestroy();
	}

	@BindClick(R.id.loginButton)
	private void onClickLogin(final Button button) {
		button.setEnabled(false);
		loginProgressDialog.show();

		sessionService.startNewSession(new Callback<Session>() {
			@Override
			public void onSuccess(Session object) {
				loginProgressDialog.dismiss();
				button.setEnabled(true);
				onLoginSuccess();
			}

			@Override
			public void onFailure(Exception caught) {
				loginProgressDialog.dismiss();
				button.setEnabled(true);
				onLoginFailure(caught);
			}
		});
	}

	private void onLoginFailure(Exception caught) {
		new AlertDialog.Builder(LoginActivity.this)
				.setMessage(caught.getMessage())
				.show();
	}

	private void onLoginSuccess() {
		if (!isFinishing()) {
			// start new Activity
			Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);

			// close this one
			finish();
		}
	}
}
