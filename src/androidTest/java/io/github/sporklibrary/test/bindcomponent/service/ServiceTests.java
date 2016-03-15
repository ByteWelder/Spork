package io.github.sporklibrary.test.bindcomponent.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;

public class ServiceTests
{
	@Rule
	public final ServiceTestRule mServiceRule = new ServiceTestRule();

	@Test
	public void test() throws TimeoutException
	{
		Intent service_intent = new Intent(InstrumentationRegistry.getTargetContext(), LocalService.class);

		// Bind the service and grab a reference to the binder.
		IBinder binder = mServiceRule.bindService(service_intent);

		assertNotNull(binder);

		// Get the reference to the service, or you can call
		// public methods on the binder directly.
		LocalService service = ((LocalService.LocalBinder)binder).getLocalService();

		assertNotNull(service);
		assertNotNull(service.getTestComponent());
	}
}
