package spork.android.example;

import javax.inject.Singleton;

import spork.android.example.services.ApiService;
import spork.android.example.services.HttpService;
import spork.android.example.services.HttpServiceImpl;
import spork.android.example.services.SessionService;
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
