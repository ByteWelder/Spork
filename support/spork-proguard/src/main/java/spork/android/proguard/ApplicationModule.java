package spork.android.proguard;

import spork.android.proguard.services.HttpService;
import spork.android.proguard.services.RestService;
import spork.inject.Provides;

public class ApplicationModule {

	@Provides
	public HttpService provideHttpService() {
		return new HttpService();
	}

	@Provides
	public RestService provideRestService(HttpService httpService) {
		return new RestService(httpService);
	}
}
