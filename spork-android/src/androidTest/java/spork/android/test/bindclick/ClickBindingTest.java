package spork.android.test.bindclick;

import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.BindFailed;
import spork.android.test.bindclick.domain.TestActivity;
import spork.android.test.bindclick.domain.TestFaultyClickArgumentsView;
import spork.android.test.bindclick.domain.TestFaultyView;
import spork.android.test.bindclick.domain.TestFragment;
import spork.android.test.bindclick.domain.TestPojo;
import spork.android.test.bindclick.domain.TestView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

public class ClickBindingTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void testMain() {
        TestActivity activity = activityRule.getActivity();
        TestFragment testFragment = activity.getTestFragment();
        TestView testView = activity.getTestView();

        assertNotNull(testFragment);
        assertNotNull(testView);

        testClick(activity);
        testClick(activity.getTestFragment());
        testClick(activity.getTestView());
    }

    @Test
    public void testPojo() {
        expectedException.expect(BindFailed.class);
        expectedException.expectMessage("cannot resolve View from spork.android.test.bindclick.domain.TestPojo");

        new TestPojo();
    }

    @Test
    public void testFaultyView() {
        expectedException.expect(BindFailed.class);
        expectedException.expectMessage("View not found");

        new TestFaultyView(activityRule.getActivity());
    }

    @Test
    public void testFaultyClickArgumentsView() {
        expectedException.expect(PerformException.class);
        expectedException.expectCause(allOf(
                isA(BindFailed.class),
                hasMessage(containsString("onClick() failed because the method arguments are invalid"))
        ));

        TestFaultyClickArgumentsView view = activityRule.getActivity().getTestFaultyClickArgumentsView();

        assertNotNull(view);
        onView(withId(view.getId())).perform(click());
    }

    private void testClick(ClickTestProvider provider) {
        String message = "click testing " + provider.getClass().getSimpleName();
        int view_id = provider.getClickViewResourceId();
        assertEquals(message + ": initial click count", 0, provider.getClickCount());
        onView(withId(view_id)).perform(click());
        assertEquals(message + ": click count", 1, provider.getClickCount());
    }
}
