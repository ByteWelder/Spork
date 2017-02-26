package spork.android.support.test.bindlayout;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import spork.android.support.test.bindlayout.domain.TestActivitySupport;

import org.junit.Rule;
import org.junit.Test;

import spork.android.support.test.R;

import static org.junit.Assert.assertNotNull;

public class LayoutBindingTestSupport {

    @Rule
    public ActivityTestRule<TestActivitySupport> activityRule = new ActivityTestRule<>(TestActivitySupport.class);

    @Test
    public void testActivity() {
        TestActivitySupport activity = activityRule.getActivity();
        View activityView = activity.findViewById(R.id.activity_layout_binding_root);
        assertNotNull(activityView);
    }
}
