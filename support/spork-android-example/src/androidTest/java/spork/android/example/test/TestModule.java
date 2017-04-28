package spork.android.example.test;

import spork.android.example.services.HttpService;
import spork.android.example.test.stubs.HttpServiceStub;
import spork.inject.Provides;

/**
 * Test module that re-defines certain dependencies.
 */
public class TestModule {

	/**
	 * @return a stubbed HttpService
	 */
	@Provides
	public HttpService provideHttpService() {
		return new HttpServiceStub();
	}
}
