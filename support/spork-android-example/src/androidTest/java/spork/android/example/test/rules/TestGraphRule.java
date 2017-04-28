package spork.android.example.test.rules;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.annotation.Nullable;

import spork.android.example.ExampleApplication;
import spork.android.example.test.TestModule;
import spork.inject.ObjectGraph;
import spork.inject.ObjectGraphs;

public class TestGraphRule implements TestRule {

	@Nullable
	private ObjectGraph testObjectGraph;

	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
				ExampleApplication application = (ExampleApplication) instrumentation.getTargetContext().getApplicationContext();
				ObjectGraph originalGraph = application.getObjectGraph();

				try {
					// Create a new graph that overrides the existing one
					testObjectGraph = ObjectGraphs.builder(application.getObjectGraph())
							.module(new TestModule())
							.build();

					// Replace Application's ObjectGraph
					application.setObjectGraph(testObjectGraph);

					base.evaluate();
				} finally {
					// Restore original graph
					application.setObjectGraph(originalGraph);
				}
			}
		};
	}

	public ObjectGraph getTestObjectGraph() {
		if (testObjectGraph == null) {
			throw new IllegalStateException("test graph not created");
		}

		return testObjectGraph;
	}
}
