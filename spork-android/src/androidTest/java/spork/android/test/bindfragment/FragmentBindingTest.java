package spork.android.test.bindfragment;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import spork.android.test.bindfragment.domain.TestActivity;

import static org.junit.Assert.assertNotNull;

public class FragmentBindingTest {

    @Rule
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void test() {
        TestActivity activity = activityRule.getActivity();
        assertNotNull("fragment by specified id", activity.getFragmentByIdSpecified());
        assertNotNull("fragment by implied id", activity.getFragmentByIdImplied());
    }
}
