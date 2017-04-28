package spork.android.test.bindview;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.BindFailed;
import spork.android.test.bindview.domain.FaultyImpliedIdView;
import spork.android.test.bindview.domain.FaultySpecifiedIdView;
import spork.android.test.bindview.domain.FaultyTargetTypeView;
import spork.android.test.bindview.domain.Pojo;
import spork.android.test.bindview.domain.TestActivity;

import static org.junit.Assert.assertNotNull;

public class ViewBindingTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Rule
	public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

	@Test
	public void run() {
		TestActivity activity = activityRule.getActivity();

		testBinding(activity);
		testBinding(activity.getViewBindingFragment());
		testBinding(activity.getViewBindingView());
	}

	@Test
	public void bindViewPojo() {
		expectedException.expect(BindFailed.class);
		expectedException.expectMessage("cannot resolve View from " + Pojo.class.getName());

		new Pojo();
	}

	@Test
	public void bindFaultyImpliedId() {
		expectedException.expect(BindFailed.class);
		expectedException.expectMessage("View not found for fallback R.id.faultyView");

		new FaultyImpliedIdView(activityRule.getActivity());
	}

	@Test
	public void bindFaultySpecifiedId() {
		expectedException.expect(BindFailed.class);
		expectedException.expectMessage("View not found");

		new FaultySpecifiedIdView(activityRule.getActivity());
	}

	@Test
	public void bindFaultyTargetType() {
		expectedException.expect(BindFailed.class);
		expectedException.expectMessage("field is not a View");

		new FaultyTargetTypeView(activityRule.getActivity());
	}

	private void testBinding(ViewProvider provider) {
		String message = "binding " + provider.getClass().getSimpleName();

		assertNotNull(message, provider.getViewByIdSpecified());
		assertNotNull(message, provider.getViewByImplied());
	}
}