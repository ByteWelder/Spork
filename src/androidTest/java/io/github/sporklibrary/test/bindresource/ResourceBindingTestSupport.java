package io.github.sporklibrary.test.bindresource;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;
import io.github.sporklibrary.test.bindresource.domain.TestActivitySupport;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SmallTest
public class ResourceBindingTestSupport
{
	@Rule
	public ActivityTestRule<TestActivitySupport> mActivityRule = new ActivityTestRule<>(TestActivitySupport.class);

	@Test
	public void run()
	{
		TestActivitySupport activity = mActivityRule.getActivity();
		assertNotNull(activity.getResourceBindingFragment());
		assertNotNull(activity.getResourceBindingView());

		testBinding(activity);
		testBinding(activity.getResourceBindingFragment());
		testBinding(activity.getResourceBindingView());
	}

	private void testBinding(ResourceProvider provider)
	{
		Resources resource = mActivityRule.getActivity().getResources();
		String message = "binding " + provider.getClass().getSimpleName();

		float dimen_target = resource.getDimension(io.github.sporklibrary.test.R.dimen.spork_test_dimension);
		float dimen_by_id = provider.getDimensionByIdSpecified();
		float dimen_by_name = provider.getDimensionByIdImplied();
		double dimen_tolerance = 0.000001;

		assertEquals(message, dimen_target, dimen_by_id, dimen_tolerance);
		assertEquals(message, dimen_target, dimen_by_name, dimen_tolerance);

		String string_target = resource.getString(io.github.sporklibrary.test.R.string.app_name);
		String string_by_id = provider.getStringByIdSpecified();
		String string_by_name = provider.getStringByIdImplied();

		assertEquals(message, string_target, string_by_id);
		assertEquals(message, string_target, string_by_name);

		Drawable drawable_by_id = provider.getDrawableByIdSpecified();
		Drawable drawable_by_name = provider.getDrawableByIdImplied();

		// Can't compare instances
		assertNotNull(message, drawable_by_id);
		assertNotNull(message, drawable_by_name);
	}
}
