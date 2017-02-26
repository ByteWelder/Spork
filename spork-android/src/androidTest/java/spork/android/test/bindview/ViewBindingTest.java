package spork.android.test.bindview;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import spork.android.test.bindview.domain.FaultyImpliedIdView;
import spork.android.test.bindview.domain.FaultySpecifiedIdView;
import spork.android.test.bindview.domain.FaultyTargetTypeView;
import spork.android.test.bindview.domain.Pojo;
import spork.android.test.bindview.domain.TestActivity;
import spork.BindException;

import static org.junit.Assert.assertNotNull;

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