package io.github.sporklibrary.android.bindfragment;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;

import io.github.sporklibrary.android.bindfragment.domain.TestActivity;

import static org.junit.Assert.assertNotNull;

@SmallTest
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
