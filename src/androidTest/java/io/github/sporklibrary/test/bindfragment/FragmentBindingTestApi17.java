package io.github.sporklibrary.test.bindfragment;

import android.os.Build;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.bindfragment.domain.TestActivityApi17;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN_MR1)
@RunWith(AndroidJUnit4.class)
@SmallTest
public class FragmentBindingTestApi17
{
	@Rule
	public ActivityTestRule<TestActivityApi17> mActivityRule = new ActivityTestRule<>(TestActivityApi17.class);

	@Test
	public void test()
	{
		TestActivityApi17 activity = mActivityRule.getActivity();

		assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
		assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());

		assertNotNull("TestFragmentApi17", activity.getTestFragment());
		assertNotNull("TestFragmentApi17 child by implied id", activity.getTestFragment().getFragmentByIdImplied());
		assertNotNull("TestFragmentApi17 child by specified id", activity.getTestFragment().getFragmentByIdSpecified());
	}
}
