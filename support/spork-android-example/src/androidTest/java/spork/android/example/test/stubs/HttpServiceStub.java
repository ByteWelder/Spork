package spork.android.example.test.stubs;

import spork.android.example.concurrent.Callback;
import spork.android.example.services.HttpService;

public class HttpServiceStub implements HttpService {

	@Override
	public void execute(Request request, Callback<Response> callback) {
		callback.onSuccess(new Response());
	}
}
