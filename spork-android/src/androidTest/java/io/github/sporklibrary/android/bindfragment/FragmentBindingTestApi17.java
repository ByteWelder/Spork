package io.github.sporklibrary.android.bindfragment;

import android.os.Build;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import io.github.sporklibrary.android.bindfragment.domain.TestActivityApi17;

import static org.junit.Assert.assertNotNull;

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FragmentBindingTestApi17 {

    @Rule
    public ActivityTestRule<TestActivityApi17> activityRule = new ActivityTestRule<>(TestActivityApi17.class);

    @Test
    public void test() {
        TestActivityApi17 activity = activityRule.getActivity();
        assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
        assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());
        assertNotNull("TestFragmentApi17", activity.getTestFragment());
        assertNotNull("TestFragmentApi17 child by implied id", activity.getTestFragment().getFragmentByIdImplied());
        assertNotNull("TestFragmentApi17 child by specified id", activity.getTestFragment().getFragmentByIdSpecified());
    }
}
