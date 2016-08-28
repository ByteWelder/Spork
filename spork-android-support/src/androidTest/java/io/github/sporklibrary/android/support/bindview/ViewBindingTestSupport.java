package io.github.sporklibrary.android.support.bindview;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.android.support.bindview.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
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

		String message = "binding " + activity.getViewBindingFragment().getClass().getSimpleName();

		assertNotNull(message, activity.getViewBindingFragment().getViewByIdSpecified());
		assertNotNull(message, activity.getViewBindingFragment().getViewByImplied());
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
			.check(matches(hasDescendant(withText("Gamma"))));
	}
}
