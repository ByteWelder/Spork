package spork.android.proguard;

import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;

public class Application extends android.app.Application {
	private ObjectGraph objectGraph;

	@Override
	public void onCreate() {
		super.onCreate();

		objectGraph = ObjectGraphs.builder()
				.module(new ApplicationModule())
				.build();
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}
}
