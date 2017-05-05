package spork.inject;

import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Scope;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjectCustomScopeTests {

	@Scope
	@Retention(RetentionPolicy.RUNTIME)
	private @interface SessionScope {
	}

	public static final class ApplicationModule {
		@Provides
		public String provideName() {
			return "My Application";
		}
	}

	public static final class SessionModule {
		private static final AtomicInteger counter = new AtomicInteger();

		@Provides
		@SessionScope
		public Integer provideSessionId() {
			return counter.incrementAndGet();
		}
	}

	public static final class ScreenModule {
		@Provides
		public String provideTitle() {
			return "Login";
		}
	}

	public static class Application {
		@Inject
		public String name;
	}

	public static class Session {
		@Inject
		public Integer sessionId;
	}

	public static class Screen {
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
		ObjectGraph appGraph = ObjectGraphs.builder()
				.module(new ApplicationModule())
				.build();
		appGraph.inject(app);

		Session session = new Session();
		ObjectGraph sessionGraph = ObjectGraphs.builder(appGraph)
				.scope(SessionScope.class)
				.module(new SessionModule())
				.build();
		sessionGraph.inject(session);

		Screen screenOne = new Screen();
		ObjectGraph screenGraphOne = ObjectGraphs.builder(sessionGraph)
				.module(new ScreenModule())
				.build();
		screenGraphOne.inject(screenOne);

		Screen screenTwo = new Screen();
		ObjectGraph screenGraphTwo = ObjectGraphs.builder(sessionGraph)
				.module(new ScreenModule())
				.build();
		screenGraphTwo.inject(screenTwo);

		assertThat(screenOne.getSessionId(), is(screenTwo.getSessionId()));
	}
}
