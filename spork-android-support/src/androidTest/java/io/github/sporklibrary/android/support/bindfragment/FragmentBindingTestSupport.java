package io.github.sporklibrary.android.support.bindfragment;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;

import io.github.sporklibrary.android.support.bindfragment.domain.TestActivitySupport;

import static org.junit.Assert.assertNotNull;

public class FragmentBindingTestSupport {

    @Rule
    public ActivityTestRule<TestActivitySupport> activityRule = new ActivityTestRule<>(TestActivitySupport.class);

    @Test
    public void test() {
        TestActivitySupport activity = activityRule.getActivity();
        assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
        assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());
        assertNotNull("TestFragmentSupport", activity.getTestFragment());
        assertNotNull("TestFragmentSupport child by implied id", activity.getTestFragment().getFragmentByIdImplied());
        assertNotNull("TestFragmentSupport child by specified id", activity.getTestFragment().getFragmentByIdSpecified());
    }
}
