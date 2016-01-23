package io.github.sporklibrary.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import io.github.sporklibrary.test.activities.TestActivity;
import io.github.sporklibrary.test.fragments.TestFragment;
import io.github.sporklibrary.test.views.TestView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest
{
	@Rule
	public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void activityViewInjection()
	{
		assertNotNull("view injection by id", mActivityRule.getActivity().getButtonById());
		assertNotNull("view injection by name", mActivityRule.getActivity().getButtonByName());
	}

	@Test
	public void fragmentViewInjection()
	{
		TestFragment fragment = mActivityRule.getActivity().getTestFragment();

		assertNotNull("fragment presence", fragment);

		assertNotNull("view injection by id", fragment.getButtonById());
		assertNotNull("view injection by name", fragment.getButtonByName());
	}

	@Test
	public void viewInjection()
	{
		// TODO: test custom view in Activity

		TestView view = new TestView(mActivityRule.getActivity());

		assertNotNull("view injection by id", view.getButtonById());
		assertNotNull("view injection by name", view.getButtonByName());
	}

	@Test(expected = RuntimeException.class)
	public void faultyInjection()
	{
		new FaultyInjection();
	}
}
