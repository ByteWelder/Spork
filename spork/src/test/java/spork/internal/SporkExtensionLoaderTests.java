package spork.internal;

import org.junit.Test;

import spork.SporkExtension;
import spork.SporkInstance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SporkExtensionLoaderTests {

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
		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, getClass().getName());
	}

	@Test
	public void extensionConstructorThrowsException() {
		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, InstantiationExceptionExtension.class.getName());
	}

	@Test
	public void extensionConstructorPrivate() {
		SporkInstance spork = new SporkInstance();
		SporkExtensionLoader.load(spork, PrivateConstructorExtension.class.getName());
	}
}
