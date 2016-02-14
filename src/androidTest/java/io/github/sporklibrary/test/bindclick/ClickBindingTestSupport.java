package io.github.sporklibrary.test.bindclick;

import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;
import io.github.sporklibrary.test.bindclick.domain.TestActivitySupport;
import io.github.sporklibrary.test.bindclick.domain.TestFaultyClickArgumentsView;
import io.github.sporklibrary.test.bindclick.domain.TestFragmentSupport;
import io.github.sporklibrary.test.bindclick.domain.TestView;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClickBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void testMain()
	{
		TestActivitySupport activity = mActivityRule.getActivity();
		TestFragmentSupport test_fragment = activity.getTestFragment();
		TestView test_view = activity.getTestView();

		assertNotNull(test_fragment);
		assertNotNull(test_view);

		testClick(activity);
		testClick(activity.getTestFragment());
		testClick(activity.getTestView());
	}

	@Test(expected = PerformException.class)
	public void testFaultyClickArgumentsView()
	{
		TestFaultyClickArgumentsView view = mActivityRule.getActivity().getTestFaultyClickArgumentsView();
		assertNotNull(view);

		onView(withId(view.getId())).perform(click());
	}

	private void testClick(ClickTestProvider provider)
	{
		String message = "click testing " + provider.getClass().getSimpleName();

		int view_id = provider.getClickViewResourceId();

		assertEquals(message + ": initial click count", 0, provider.getClickCount());
		onView(withId(view_id)).perform(click());
		assertEquals(message + ": click count", 1, provider.getClickCount());
	}
}
