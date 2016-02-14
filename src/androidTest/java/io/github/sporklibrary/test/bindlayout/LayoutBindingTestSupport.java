package io.github.sporklibrary.test.bindlayout;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import io.github.sporklibrary.test.bindlayout.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;
import io.github.sporklibrary.test.R;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class LayoutBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void testActivity()
	{
		TestActivitySupport activity = mActivityRule.getActivity();

		View activity_view = activity.findViewById(R.id.activity_layout_binding_root);
		assertNotNull(activity_view);
	}
}
