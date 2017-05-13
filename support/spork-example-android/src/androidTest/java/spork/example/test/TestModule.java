package spork.example.test;

import example.spork.services.HttpService;
import spork.example.test.stubs.HttpServiceStub;
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
