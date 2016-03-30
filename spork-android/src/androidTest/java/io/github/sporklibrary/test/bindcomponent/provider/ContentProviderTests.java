package io.github.sporklibrary.test.bindcomponent.provider;

import android.test.ProviderTestCase2;

public class ContentProviderTests extends ProviderTestCase2<TestContentProvider> {

    public ContentProviderTests() {
        super(TestContentProvider.class, "sporktest");
    }

    public void testComponent() {
        TestContentProvider provider = getProvider();
        assertNotNull(provider);
        assertNotNull(provider.getTestComponent());
    }
}
