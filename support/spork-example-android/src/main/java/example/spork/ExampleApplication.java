package example.spork;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphProvider;
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

	@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
	public void setObjectGraph(ObjectGraph objectGraph) {
		this.objectGraph = objectGraph;
	}
}
