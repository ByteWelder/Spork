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
public class LayoutBindingTest
{
	@Rule
	public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void testActivity()
	{
		TestActivity activity = mActivityRule.getActivity();

		View activity_view = activity.findViewById(R.id.activity_layout_binding_root);
		assertNotNull(activity_view);
	}

	@Test
	public void testView()
	{
		TestView test_view = new TestView(mActivityRule.getActivity());

		View root_view = test_view.findViewById(R.id.view_layout_binding_linearlayout);
		assertNotNull(root_view);
	}

	@Test(expected = Resources.NotFoundException.class)
	public void faultyLayoutTestView()
	{
		new FaultyLayoutTestView(mActivityRule.getActivity());
	}

	@Test(expected = BindException.class)
	public void pojo()
	{
		new Pojo();
	}
}
