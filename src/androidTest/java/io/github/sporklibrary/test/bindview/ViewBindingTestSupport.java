package io.github.sporklibrary.test.bindview;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.bindview.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class ViewBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void run()
	{
		TestActivitySupport activity = mActivityRule.getActivity();

		testBinding(activity);
		testBinding(activity.getViewBindingFragment());
		testBinding(activity.getViewBindingView());
	}

	private void testBinding(ViewProvider provider)
	{
		String message = "binding " + provider.getClass().getSimpleName();

		assertNotNull(message, provider.getViewByIdSpecified());
		assertNotNull(message, provider.getViewByImplied());
	}
}
