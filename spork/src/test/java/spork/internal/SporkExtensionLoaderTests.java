package spork.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.SporkExtension;
import spork.SporkInstance;
import spork.exceptions.ExtensionLoadingFailed;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SporkExtensionLoaderTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	final static class Extension implements SporkExtension {
		static int initializeCallCount = 0;

		@Override
		public void initialize(SporkInstance spork) {
			initializeCallCount++;
		}
	}

	final static class InstantiationExceptionExtension implements SporkExtension {

		public InstantiationExceptionExtension() throws InstantiationException {
			throw new InstantiationException();
		}

		@Override
		public void initialize(SporkInstance spork) {
		}
	}

	final static class PrivateConstructorExtension implements SporkExtension {

		private PrivateConstructorExtension() {
			// nothing
		}

		@Override
		public void initialize(SporkInstance spork) {
		}
	}

	@Test
	public void validExtension() {
		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, Extension.class.getName());

		assertThat(Extension.initializeCallCount, is(1));
	}

	@Test
	public void extensionClassNotFound() {
		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, "foo");
	}

	@Test
	public void extensionIsWrongType() {
		expectedException.expect(ExtensionLoadingFailed.class);
		expectedException.expectMessage("Extension " + getClass().getName() + " does not implement interface " + SporkExtension.class.getName());

		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, getClass().getName());
	}

	@Test
	public void extensionConstructorThrowsException() {
		expectedException.expect(ExtensionLoadingFailed.class);
		expectedException.expectMessage("Failed to create an instance of " + InstantiationExceptionExtension.class.getName());
		expectedException.expectCause(any(InstantiationException.class));

		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, InstantiationExceptionExtension.class.getName());
	}

	@Test
	public void extensionConstructorPrivate() {
		expectedException.expect(ExtensionLoadingFailed.class);
		expectedException.expectMessage("Failed to initialize " + PrivateConstructorExtension.class.getName() + " because of an IllegalAccessException");
		expectedException.expectCause(any(IllegalAccessException.class));

		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, PrivateConstructorExtension.class.getName());
	}
}