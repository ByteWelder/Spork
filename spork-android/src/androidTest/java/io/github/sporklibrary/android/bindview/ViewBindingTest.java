package io.github.sporklibrary.android.bindview;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.android.bindview.domain.*;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class ViewBindingTest
{
	@Rule
	public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void run()
	{
		TestActivity activity = mActivityRule.getActivity();

		testBinding(activity);
		testBinding(activity.getViewBindingFragment());
		testBinding(activity.getViewBindingView());
	}

	@Test(expected = BindException.class)
	public void bindViewPojo()
	{
		new Pojo();
	}

	@Test(expected = BindException.class)
	public void bindFaultyImpliedId()
	{
		new FaultyImpliedIdView(mActivityRule.getActivity());
	}

	@Test(expected = BindException.class)
	public void bindFaultySpecifiedId()
	{
		new FaultySpecifiedIdView(mActivityRule.getActivity());
	}

	@Test(expected = BindException.class)
	public void bindFaultyTargetType()
	{
		new FaultyTargetTypeView(mActivityRule.getActivity());
	}

	private void testBinding(ViewProvider provider)
	{
		String message = "binding " + provider.getClass().getSimpleName();

		assertNotNull(message, provider.getViewByIdSpecified());
		assertNotNull(message, provider.getViewByImplied());
	}
}