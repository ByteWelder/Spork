package spork.example.test.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import example.spork.activities.LaunchActivity;
import example.spork.activities.LoginActivity;
import example.spork.activities.WelcomeActivity;
import example.spork.services.Session;
import example.spork.services.SessionService;
import spork.example.test.rules.ImmediateIntentsTestRule;
import spork.example.test.rules.TestGraphRule;
import spork.example.test.stubs.NoopCallback;

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
