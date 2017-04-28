package spork.android.example.services;

import spork.android.example.concurrent.Callback;

/**
 * Dummy ApiService that uses {@link HttpServiceImpl} to execute requests to a backend.
 */
public class ApiService {
	private static final String BASE_URL = "http://localhost";
	private final HttpService httpService;

	public ApiService(HttpService httpService) {
		this.httpService = httpService;
	}

	public void execute(Session session, String apiPath, Callback<HttpService.Response> callback) {
		HttpService.Request request = new HttpService.Request(BASE_URL + apiPath);
		httpService.execute(request, callback);
	}
}
