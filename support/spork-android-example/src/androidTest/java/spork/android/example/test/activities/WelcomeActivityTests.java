package spork.android.example.test.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import spork.android.example.R;
import spork.android.example.activities.WelcomeActivity;
import spork.android.example.services.Session;
import spork.android.example.services.SessionService;
import spork.android.example.test.rules.ImmediateIntentsTestRule;
import spork.android.example.test.rules.TestGraphRule;
import spork.android.example.test.stubs.NoopCallback;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class WelcomeActivityTests {

	@Rule
	public TestGraphRule testGraphRule = new TestGraphRule();

	@Rule
	public ImmediateIntentsTestRule intentsTestRule = new ImmediateIntentsTestRule();

	@Rule
	public ActivityTestRule<WelcomeActivity> activityRule = new ActivityTestRule<>(WelcomeActivity.class, false, false);

	@Inject
	private SessionService sessionService;

	@Before
	public void setup() {
		// Ensure we have a SessionService
		testGraphRule.getTestObjectGraph().inject(this);
	}

	@Test
	public void startWithInactiveSession() {
		activityRule.launchActivity(new Intent());

		assertThat(activityRule.getActivity().isFinishing(), is(true));
	}

	@Test
	public void startWithActiveSession() {
		// ensure there is an active session
		sessionService.startNewSession(new NoopCallback<Session>());

		activityRule.launchActivity(new Intent());

		assertThat(activityRule.getActivity().isFinishing(), is(false));
	}

	@Test
	public void clickLogout() {
		// ensure there is an active session
		sessionService.startNewSession(new NoopCallback<Session>());

		activityRule.launchActivity(new Intent());

		onView(withId(R.id.logoutButton)).perform(click());

		assertThat(activityRule.getActivity().isFinishing(), is(true));
	}
}
