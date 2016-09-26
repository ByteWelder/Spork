package io.github.sporklibrary.android.support.bindlayout;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import io.github.sporklibrary.android.support.bindlayout.domain.TestActivitySupport;

import org.junit.Rule;
import org.junit.Test;

import io.github.sporklibrary.android.support.test.R;

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
