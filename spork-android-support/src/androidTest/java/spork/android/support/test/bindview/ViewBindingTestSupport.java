package spork.android.support.test.bindview;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;

import spork.android.support.test.R;
import spork.android.support.test.bindview.domain.TestActivitySupport;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

public class ViewBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> activityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void testMain()
	{
		TestActivitySupport activity = activityRule.getActivity();

		String message = "binding " + activity.getViewBindingFragment().getClass().getSimpleName();

		assertNotNull(message, activity.getViewBindingFragment().getViewByIdSpecified());
		assertNotNull(message, activity.getViewBindingFragment().getViewByImplied());
	}

	@Test
	public void testRecyclerView()
	{
		TestActivitySupport activity = activityRule.getActivity();

		RecyclerView recycler_view = activity.getRecyclerView();

		assertNotNull(recycler_view);

		onView(withId(R.id.test_recyclerview))
			.check(matches(hasDescendant(withText("Alpha"))))
			.check(matches(hasDescendant(withText("Beta"))))
			.check(matches(hasDescendant(withText("Gamma"))));
	}
}
