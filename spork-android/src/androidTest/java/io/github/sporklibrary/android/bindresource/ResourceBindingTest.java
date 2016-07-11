package io.github.sporklibrary.android.bindresource;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;

import io.github.sporklibrary.android.bindresource.domain.TestActivity;
import io.github.sporklibrary.android.bindresource.domain.TestDimensionPojo;
import io.github.sporklibrary.android.bindresource.domain.TestDrawablePojo;
import io.github.sporklibrary.android.bindresource.domain.TestStringPojo;
import io.github.sporklibrary.android.test.R;
import io.github.sporklibrary.exceptions.BindException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SmallTest
public class ResourceBindingTest {

    @Rule
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void run() {
        TestActivity activity = activityRule.getActivity();
        assertNotNull(activity.getResourceBindingFragment());
        assertNotNull(activity.getResourceBindingView());

        testBinding(activity);
        testBinding(activity.getResourceBindingFragment());
        testBinding(activity.getResourceBindingView());
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

    @Test(expected = BindException.class)
    public void testDimensionPojo() {
        new TestDimensionPojo();
    }

    @Test(expected = BindException.class)
    public void testDrawablePojo() {
        new TestDrawablePojo();
    }

    @Test(expected = BindException.class)
    public void testStringPojo() {
        new TestStringPojo();
    }
}