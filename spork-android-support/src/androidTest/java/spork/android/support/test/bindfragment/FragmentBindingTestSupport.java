package spork.android.support.test.bindfragment;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import spork.android.support.test.bindfragment.domain.TestActivitySupport;

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
