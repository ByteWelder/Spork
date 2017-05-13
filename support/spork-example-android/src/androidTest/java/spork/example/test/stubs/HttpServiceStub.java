package spork.example.test.stubs;

import example.spork.concurrent.Callback;
import example.spork.services.HttpService;

public class HttpServiceStub implements HttpService {

	@Override
	public void execute(Request request, Callback<Response> callback) {
		callback.onSuccess(new Response());
	}
}
