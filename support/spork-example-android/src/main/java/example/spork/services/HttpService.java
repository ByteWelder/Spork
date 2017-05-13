package example.spork.services;

import example.spork.concurrent.Callback;

public interface HttpService {
	void execute(Request request, Callback<Response> callback);

	class Request {
		public Request(String url) {
		}
	}

	class Response {
	}
}
