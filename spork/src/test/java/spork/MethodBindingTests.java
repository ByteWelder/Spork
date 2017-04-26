package spork;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;

import spork.stubs.TestAnnotation;
import spork.stubs.TestMethodBinder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MethodBindingTests {
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	private final SporkInstance spork = new SporkInstance();
	private TestMethodBinder bindMethodBinder;

	private static class PrivateMethodParent {
		@TestAnnotation
		private void method() {
		}
	}

	private static class PublicMethodParent {
		@TestAnnotation
		public void method() {
		}
	}

	private static class ProtectedMethodParent {
		@TestAnnotation
		private void method() {
		}
	}

	private static class StaticMethodParent {
		@TestAnnotation
		private static void method() {
		}
	}

	@Before
	public void registerTestBinders() {
		bindMethodBinder = spy(new TestMethodBinder());
		spork.register(bindMethodBinder);
	}

	@Test
	public void bindPrivateMethod() {
		testMethodBindSuccess(new PrivateMethodParent());
	}

	@Test
	public void bindPublicMethod() {
		testMethodBindSuccess(new PublicMethodParent());
	}

	@Test
	public void bindProtectedMethod() {
		testMethodBindSuccess(new ProtectedMethodParent());
	}

	@Test
	public void bindStaticMethod() {
		testMethodBindSuccess(new StaticMethodParent());
	}

	private void testMethodBindSuccess(Object toBind) {
		spork.bind(toBind);
		verify(bindMethodBinder).bind(
				eq(toBind),
				any(TestAnnotation.class),
				any(Method.class),
				any(Object[].class));
	}
}
