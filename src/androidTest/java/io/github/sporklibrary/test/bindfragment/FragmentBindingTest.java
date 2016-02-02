package io.github.sporklibrary.test.bindfragment;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.bindfragment.domain.TestActivity;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class FragmentBindingTest
{
	@Rule
	public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void test()
	{
		TestActivity activity = mActivityRule.getActivity();

		assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
		assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());
	}
}
