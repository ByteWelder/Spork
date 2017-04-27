package spork.android.proguard;

import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

public class Application extends android.app.Application {
	private ObjectGraph objectGraph;

	@Override
	public void onCreate() {
		super.onCreate();

		objectGraph = new ObjectGraphBuilder()
				.module(new ApplicationModule())
				.build();
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}
}
