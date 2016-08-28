package io.github.sporklibrary.android.bindcomponent.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

public class ServiceTests {

    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();

    @Test
    public void test() throws TimeoutException {
        Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), TestService.class);

        // Bind the service and grab a reference to the binder.
        IBinder binder = serviceRule.bindService(serviceIntent);

        assertNotNull(binder);

        // Get the reference to the service, or you can call
        // public methods on the binder directly.
        TestService service = ((TestService.LocalBinder) binder).getLocalService();

        assertNotNull(service);
        assertNotNull(service.getTestComponent());
    }
}
