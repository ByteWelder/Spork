package spork.android.example.services;

import spork.android.example.concurrent.Callback;

public interface HttpService {
	void execute(Request request, Callback<Response> callback);

	class Request {
		public Request(String url) {
		}
	}

	class Response {
	}
}
