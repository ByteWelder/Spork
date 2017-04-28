package spork.android.support.test.bindclick;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;

import spork.android.support.test.bindclick.domain.RecyclerViewAdapter;
import spork.android.support.test.bindclick.domain.TestActivitySupport;
import spork.android.support.test.bindclick.domain.TestFragmentSupport;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClickBindingTestSupport {
	@Rule
	public ActivityTestRule<TestActivitySupport> activityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void testMain() {
		TestActivitySupport activity = activityRule.getActivity();
		TestFragmentSupport test_fragment = activity.getTestFragment();

		assertNotNull(test_fragment);
		assertNotNull(activity.getTestView());

		testClick(activity);
		testClick(activity.getTestFragment());
	}

	@Test
	public void testRecyclerView() {
		RecyclerView recycler_view = activityRule.getActivity().getRecyclerView();

		assertNotNull(recycler_view);
		assertNotNull(recycler_view.getAdapter());

		RecyclerViewAdapter test_adapter = (RecyclerViewAdapter) recycler_view.getAdapter();

		assertEquals(0, test_adapter.getClickCount());

		onView(ViewMatchers.withId(spork.android.support.test.R.id.test_recyclerview))
				.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		assertEquals(1, test_adapter.getClickCount());

		onView(ViewMatchers.withId(spork.android.support.test.R.id.test_recyclerview))
				.perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

		assertEquals(2, test_adapter.getClickCount());
	}

	private void testClick(ClickTestProvider provider) {
		String message = "click testing " + provider.getClass().getSimpleName();

		int view_id = provider.getClickViewResourceId();

		assertEquals(message + ": initial click count", 0, provider.getClickCount());
		onView(withId(view_id)).perform(click());
		assertEquals(message + ": click count", 1, provider.getClickCount());
	}
}
