package spork.android.example.services;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import spork.android.example.concurrent.Callback;

/**
 * Dummy HTTP service that always returns success after a small delay.
 */
public class HttpService {
	private final Handler uiHandler;
	private final Handler backgroundHandler;

	public HttpService() {
		uiHandler = new Handler(Looper.getMainLooper());
		HandlerThread backgroundHandlerThread = new HandlerThread("HttpThread");
		backgroundHandlerThread.start();
		backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
	}

	public void execute(Request request, final Callback<Response> callback) {
		backgroundHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Post success on UI thread
				uiHandler.post(new Runnable() {
					@Override
					public void run() {
						callback.onSuccess(new Response());
					}
				});
			}
		});
	}

	public static class Request {
		public Request(String url) {
		}

		public void addHeader(String name, String value) {
		}
	}

	public static class Response {
	}
}
