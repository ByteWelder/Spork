package io.github.sporklibrary.test.contextprovider;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;

import io.github.sporklibrary.test.contextprovider.domain.TestActivity;
import io.github.sporklibrary.test.contextprovider.domain.TestComponent;

import org.junit.Rule;

import static org.junit.Assert.assertNotNull;

@SmallTest
public class ContextProviderTest {
    
    @Rule
    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

    public void test() {
        TestActivity activity = mActivityRule.getActivity();

        TestComponent component = activity.getTestComponent();
        assertNotNull(component);
        assertNotNull(component.getDrawable());
    }
}
