package io.github.sporklibrary.test;

import android.os.Build;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.activities.Api17TestActivity;
import io.github.sporklibrary.test.fragments.FragmentBindingFragment;
import io.github.sporklibrary.test.views.TestView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN_MR1)
@RunWith(AndroidJUnit4.class)
@SmallTest
public class Api17ActivityTest
{
	@Rule
	public ActivityTestRule<Api17TestActivity> mActivityRule = new ActivityTestRule<>(Api17TestActivity.class);

	@Test
	public void activityFragmentBinding()
	{
		FragmentBindingFragment fragment_binding_fragment = mActivityRule.getActivity().getFragmentBindingFragment();

		assertNotNull("find fragment by implied id", fragment_binding_fragment);

		assertNotNull("find embedded fragment by implied id", fragment_binding_fragment.getFragmentByIdImplied());
		assertNotNull("find embedded fragment by specified id", fragment_binding_fragment.getFragmentByIdSpecified());

		// TestView
		TestView test_view = fragment_binding_fragment.getTestView();
		assertNotNull("find embedded view by id", test_view);
	}
}
