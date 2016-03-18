package io.github.sporklibrary.test.bindview;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindview.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

@SmallTest
public class ViewBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void testMain()
	{
		TestActivitySupport activity = mActivityRule.getActivity();

		testBinding(activity);
		testBinding(activity.getViewBindingFragment());
		testBinding(activity.getViewBindingView());
	}

	@Test
	public void testRecyclerView()
	{
		TestActivitySupport activity = mActivityRule.getActivity();

		RecyclerView recycler_view = activity.getRecyclerView();

		assertNotNull(recycler_view);

		onView(withId(R.id.test_recyclerview))
			.check(matches(hasDescendant(withText("Alpha"))))
			.check(matches(hasDescendant(withText("Beta"))))
			.check(matches(hasDescendant(withText("Gamma"))))
			.check(matches(hasDescendant(withText("Delta"))));
	}

	private void testBinding(ViewProvider provider)
	{
		String message = "binding " + provider.getClass().getSimpleName();

		assertNotNull(message, provider.getViewByIdSpecified());
		assertNotNull(message, provider.getViewByImplied());
	}
}
