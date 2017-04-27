package spork.android.example;

import android.app.Application;

import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;

public class ExampleApplication extends Application implements ObjectGraphProvider {
	private ObjectGraph objectGraph;

	@Override
	public void onCreate() {
		super.onCreate();

		objectGraph = ObjectGraphs.builder()
				.module(new ExampleApplicationModule())
				.build();
	}

	@Override
	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}
}
