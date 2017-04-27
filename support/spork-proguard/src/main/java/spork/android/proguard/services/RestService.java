package spork.android.proguard.services;

import android.util.Log;

public class RestService {
	private HttpService httpService;

	public RestService(HttpService httpService) {
		this.httpService = httpService;
	}

	public void doSomething() {
		Log.i("RestService", "Hello World!");
	}
}
