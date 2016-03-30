package io.github.sporklibrary.test.bindlayout;

import android.content.res.Resources;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.R;
import io.github.sporklibrary.test.bindlayout.domain.FaultyLayoutTestView;
import io.github.sporklibrary.test.bindlayout.domain.Pojo;
import io.github.sporklibrary.test.bindlayout.domain.TestActivity;
import io.github.sporklibrary.test.bindlayout.domain.TestView;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class LayoutBindingTest {

    @Rule
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void testActivity() {
        TestActivity activity = activityRule.getActivity();

        View activityView = activity.findViewById(R.id.activity_layout_binding_root);
        assertNotNull(activityView);
    }

    @Test
    public void testView() {
        TestView testView = new TestView(activityRule.getActivity());
        View rootView = testView.findViewById(R.id.view_layout_binding_linearlayout);
        assertNotNull(rootView);
    }

    @Test(expected = Resources.NotFoundException.class)
    public void faultyLayoutTestView() {
        new FaultyLayoutTestView(activityRule.getActivity());
    }

    @Test(expected = BindException.class)
    public void pojo() {
        new Pojo();
    }
}
