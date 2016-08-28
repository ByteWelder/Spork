package io.github.sporklibrary.android.support.bindresource;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import io.github.sporklibrary.android.support.test.R;
import io.github.sporklibrary.android.support.bindresource.domain.TestActivitySupport;

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

        testBinding(activity);
        testBinding(activity.getResourceBindingFragment());
    }

    private void testBinding(ResourceProvider provider) {
        Resources resource = activityRule.getActivity().getResources();
        String message = "binding " + provider.getClass().getSimpleName();

        float dimenTarget = resource.getDimension(R.dimen.spork_test_dimension);
        float dimenById = provider.getDimensionByIdSpecified();
        float dimenByName = provider.getDimensionByIdImplied();
        double dimenTolerance = 0.000001;

        assertEquals(message, dimenTarget, dimenById, dimenTolerance);
        assertEquals(message, dimenTarget, dimenByName, dimenTolerance);

        String stringTarget = resource.getString(R.string.app_name);
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
