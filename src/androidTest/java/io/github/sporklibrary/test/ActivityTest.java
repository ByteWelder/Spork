package io.github.sporklibrary.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.activities.TestActivity;
import io.github.sporklibrary.test.fragments.ViewBindingFragment;
import io.github.sporklibrary.test.views.BadBindingView;
import io.github.sporklibrary.test.views.BindClickPojo;
import io.github.sporklibrary.test.views.BindViewPojo;
import io.github.sporklibrary.test.views.TestView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ActivityTest
{
	@Rule
	public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void activityViewBinding()
	{
		assertNotNull("view binding by id", mActivityRule.getActivity().getButtonByIdSpecified());
		assertNotNull("view binding by name", mActivityRule.getActivity().getButtonByIdImplied());
	}

	@Test
	public void activityFragmentBinding()
	{
		ViewBindingFragment view_binding_fragment = mActivityRule.getActivity().getViewBindingFragment();

		assertEquals(0, view_binding_fragment.getButtonClickCount());
		onView(withId(R.id.vbf_button)).perform(click());
		assertEquals(1, view_binding_fragment.getButtonClickCount());

		assertNotNull("find fragment by specified id", view_binding_fragment);

		// Test Fragment's Views
		assertNotNull("view binding by id", view_binding_fragment.getButtonByIdSpecified());
		assertNotNull("view binding by name", view_binding_fragment.getButtonByIdImplied());
	}

	@Test
	public void viewBinding()
	{
		TestView view = mActivityRule.getActivity().getTestView();

		assertNotNull(view);
		assertNotNull("view binding by id", view.getButtonByIdImplied());
		assertNotNull("view binding by name", view.getButtonByIdSpecified());

		assertEquals(0, view.getButtonClickCount());
		onView(withId(R.id.activity_testview)).perform(click());
		assertEquals(1, view.getButtonClickCount());
	}

	@Test(expected = BindException.class)
	public void bindViewPojo()
	{
		new BindViewPojo();
	}

	@Test(expected = BindException.class)
	public void badBindingView()
	{
		new BadBindingView(mActivityRule.getActivity());
	}

	@Test(expected = BindException.class)
	public void bindClickPojo()
	{
		new BindClickPojo();
	}

	@Test
	public void clicks()
	{
		assertFalse(mActivityRule.getActivity().hasClickedButtonOne());
		assertFalse(mActivityRule.getActivity().hasClickedButtonTwo());

		onView(withId(R.id.button_one)).perform(click());
		onView(withId(R.id.button_two)).perform(click());

		assertTrue(mActivityRule.getActivity().hasClickedButtonOne());
		assertTrue(mActivityRule.getActivity().hasClickedButtonTwo());
	}
}
