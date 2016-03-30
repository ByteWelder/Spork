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
public class ResourceBindingTestSupport {

    @Rule
    public ActivityTestRule<TestActivitySupport> activityRule = new ActivityTestRule<>(TestActivitySupport.class);

    @Test
    public void run() {
        TestActivitySupport activity = activityRule.getActivity();
        assertNotNull(activity.getResourceBindingFragment());
        assertNotNull(activity.getResourceBindingView());

        testBinding(activity);
        testBinding(activity.getResourceBindingFragment());
        testBinding(activity.getResourceBindingView());
    }

    private void testBinding(ResourceProvider provider) {
        Resources resource = activityRule.getActivity().getResources();
        String message = "binding " + provider.getClass().getSimpleName();

        float dimenTarget = resource.getDimension(io.github.sporklibrary.test.R.dimen.spork_test_dimension);
        float dimenById = provider.getDimensionByIdSpecified();
        float dimenByName = provider.getDimensionByIdImplied();
        double dimenTolerance = 0.000001;

        assertEquals(message, dimenTarget, dimenById, dimenTolerance);
        assertEquals(message, dimenTarget, dimenByName, dimenTolerance);

        String stringTarget = resource.getString(io.github.sporklibrary.test.R.string.app_name);
        String stringById = provider.getStringByIdSpecified();
        String stringByName = provider.getStringByIdImplied();

        assertEquals(message, stringTarget, stringById);
        assertEquals(message, stringTarget, stringByName);

        Drawable drawableById = provider.getDrawableByIdSpecified();
        Drawable drawableByName = provider.getDrawableByIdImplied();

        // Can't compare instances
        assertNotNull(message, drawableById);
        assertNotNull(message, drawableByName);
    }
}
