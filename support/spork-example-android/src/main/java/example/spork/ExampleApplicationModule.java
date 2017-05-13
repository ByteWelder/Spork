package example.spork;

import javax.inject.Singleton;

import example.spork.services.ApiService;
import example.spork.services.HttpService;
import example.spork.services.HttpServiceImpl;
import example.spork.services.SessionService;
import spork.inject.Provides;

public class ExampleApplicationModule {
	@Provides
	public HttpService provideHttpService() {
		return new HttpServiceImpl();
	}

	@Provides
	public ApiService provideApiService(HttpService httpService) {
		return new ApiService(httpService);
	}

	@Singleton
	@Provides
	public SessionService provideSessionService(ApiService apiService) {
		return new SessionService(apiService);
	}
}
