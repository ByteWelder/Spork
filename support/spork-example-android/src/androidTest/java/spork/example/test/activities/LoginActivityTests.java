package spork.example.test.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import example.spork.R;
import example.spork.activities.LoginActivity;
import example.spork.activities.WelcomeActivity;
import spork.example.test.rules.ImmediateIntentsTestRule;
import spork.example.test.rules.TestGraphRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTests {

	@Rule
	public TestGraphRule testGraphRule = new TestGraphRule();

	@Rule
	public ImmediateIntentsTestRule intentsTestRule = new ImmediateIntentsTestRule();

	@Rule
	public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class, false, false);

	@Test
	public void login() {
		activityRule.launchActivity(new Intent());

		onView(withId(R.id.loginButton)).perform(click());
		intended(hasComponent(WelcomeActivity.class.getName()));
	}
}