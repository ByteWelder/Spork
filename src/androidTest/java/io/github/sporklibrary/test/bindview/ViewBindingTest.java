package io.github.sporklibrary.test.bindview;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.bindview.domain.TestActivity;
import io.github.sporklibrary.test.bindview.domain.CorruptTestView;
import io.github.sporklibrary.test.bindview.domain.Pojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
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
	public void badBindingView()
	{
		new CorruptTestView(mActivityRule.getActivity());
	}

	private void testBinding(ViewProvider provider)
	{
		String message = "binding " + provider.getClass().getSimpleName();

		assertNotNull(message, provider.getViewByIdSpecified());
		assertNotNull(message, provider.getViewByImplied());
	}
}