package io.github.sporklibrary.test.bindfragment;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.bindfragment.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class FragmentBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void test()
	{
		TestActivitySupport activity = mActivityRule.getActivity();

		assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
		assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());

		assertNotNull("TestFragmentSupport", activity.getTestFragment());
		assertNotNull("TestFragmentSupport child by implied id", activity.getTestFragment().getFragmentByIdImplied());
		assertNotNull("TestFragmentSupport child by specified id", activity.getTestFragment().getFragmentByIdSpecified());
	}
}
