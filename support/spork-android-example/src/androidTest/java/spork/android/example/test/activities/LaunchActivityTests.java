package spork.android.example.test.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import spork.android.example.activities.LaunchActivity;
import spork.android.example.activities.LoginActivity;
import spork.android.example.activities.WelcomeActivity;
import spork.android.example.services.Session;
import spork.android.example.services.SessionService;
import spork.android.example.test.rules.ImmediateIntentsTestRule;
import spork.android.example.test.rules.TestGraphRule;
import spork.android.example.test.stubs.NoopCallback;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class LaunchActivityTests {

	@Rule
	public TestGraphRule testGraphRule = new TestGraphRule();

	@Rule
	public ImmediateIntentsTestRule intentsTestRule = new ImmediateIntentsTestRule();

	@Rule
	public ActivityTestRule<LaunchActivity> activityRule = new ActivityTestRule<>(LaunchActivity.class, false, false);

	@Inject
	private SessionService sessionService;

	@Before
	public void setup() {
		// Ensure we have a SessionService
		testGraphRule.getTestObjectGraph().inject(this);
	}

	@Test
	public void inactiveSessionIntentForwarding() {
		activityRule.launchActivity(new Intent());
		intended(hasComponent(LoginActivity.class.getName()));
	}

	@Test
	public void activeSessionIntentForwarding() {
		// Ensure we have a valid session
		sessionService.startNewSession(new NoopCallback<Session>());

		// Launch Activity
		activityRule.launchActivity(new Intent());

		// Ensure the WelcomeActivity is launched because we should be logged in
		intended(hasComponent(WelcomeActivity.class.getName()));
	}
}
