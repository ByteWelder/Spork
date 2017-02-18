package spork.android.test.bindresource;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import spork.android.test.bindresource.domain.TestActivity;
import spork.android.test.bindresource.domain.TestDimensionPojo;
import spork.android.test.bindresource.domain.TestDrawablePojo;
import spork.android.test.bindresource.domain.TestStringPojo;
import spork.android.test.R;
import spork.exceptions.BindException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        int testInt = provider.getIntByIdImplied();
        Integer testInteger = provider.getIntegerByIdSpecified();
        assertEquals(1, testInt);
        assertEquals(Integer.valueOf(1), testInteger);

        boolean testBoolean = provider.getBooleanByIdImplied();
        Boolean testBooleanObject = provider.getBooleanByIdSpecified();
        assertEquals(1, testInt);
        assertEquals(Integer.valueOf(1), testInteger);
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