package spork.android.test.bindlayout;

import android.content.res.Resources;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.BindFailed;
import spork.android.test.R;
import spork.android.test.bindlayout.domain.FaultyLayoutTestView;
import spork.android.test.bindlayout.domain.Pojo;
import spork.android.test.bindlayout.domain.TestActivity;
import spork.android.test.bindlayout.domain.TestView;

import static org.junit.Assert.assertNotNull;

public class LayoutBindingTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void faultyLayoutTestView() {
        expectedException.expect(Resources.NotFoundException.class);

        new FaultyLayoutTestView(activityRule.getActivity());
    }

    @Test
    public void pojo() {
        expectedException.expect(BindFailed.class);
        expectedException.expectMessage("annotation can only be used with Activity or ViewGroup");

        new Pojo();
    }
}
