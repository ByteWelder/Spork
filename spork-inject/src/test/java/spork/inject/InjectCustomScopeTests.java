package spork.inject;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Scope;

import spork.inject.internal.ObjectGraph;
import spork.inject.internal.ObjectGraphBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectCustomScopeTests {

	@Scope
	@Retention(RetentionPolicy.RUNTIME)
	private @interface SessionScope {
	}

	private static final class ApplicationModule {
		@Provides
		public String provideName() {
			return "My Application";
		}
	}

	private static final class SessionModule {
		private static final AtomicInteger counter = new AtomicInteger();

		@Provides
		@SessionScope
		public Integer provideSessionId() {
			return counter.incrementAndGet();
		}
	}

	private static final class ScreenModule {
		@Provides
		public String provideTitle() {
			return "Login";
		}
	}

	private static class Application {
		@Inject
		public String name;
	}

	private static class Session {
		@Inject
		public Integer sessionId;
	}

	private static class Screen {
		@Inject
		public Integer sessionId;

		@Inject
		public String title;

		public Integer getSessionId() {
			return sessionId;
		}
	}

	@Test
	public void test() {
		Application app = new Application();
		ObjectGraph appGraph = new ObjectGraphBuilder()
				.module(new ApplicationModule())
				.build();
		appGraph.inject(app);

		Session session = new Session();
		ObjectGraph sessionGraph = new ObjectGraphBuilder(appGraph)
				.scope(SessionScope.class)
				.module(new SessionModule())
				.build();
		sessionGraph.inject(session);

		Screen screenOne = new Screen();
		ObjectGraph screenGraphOne = new ObjectGraphBuilder(sessionGraph)
				.module(new ScreenModule())
				.build();
		screenGraphOne.inject(screenOne);

		Screen screenTwo = new Screen();
		ObjectGraph screenGraphTwo = new ObjectGraphBuilder(sessionGraph)
				.module(new ScreenModule())
				.build();
		screenGraphTwo.inject(screenTwo);

		assertThat(screenOne.getSessionId(), is(screenTwo.getSessionId()));
	}
}