package spork;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import spork.internal.Reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MethodBindingTests {
	private final Spork spork = new Spork();
	private BindMethodBinder bindMethodBinder;

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	private @interface BindMethod {
	}

	private static class BindMethodBinder implements MethodBinder<BindMethod> {
		private int methodCount = 0;

		@Override
		public void bind(Object instance, BindMethod annotation, Method method, Object... parameters) {
			methodCount++;
		}

		@Override
		public Class<BindMethod> getAnnotationClass() {
			return BindMethod.class;
		}

		public int getMethodCount() {
			return methodCount;
		}
	}

	private static class MethodBinderParent {
		private int testCallCount = 0;

		@BindMethod
		private void privateCallCountMethod() {
			testCallCount++;
		}

		@BindMethod
		public static int staticEchoMethod(int a) {
			return a;
		}

		public int getPrivateCallCount() {
			return testCallCount;
		}
	}

	@Before
	public void registerTestBinders() {
		bindMethodBinder = new BindMethodBinder();
		spork.getBinderRegistry().register(bindMethodBinder);
	}

	@Test
	public void methodBinding() {
		assertEquals(bindMethodBinder.getMethodCount(), 0);

		MethodBinderParent methodBinderParent = new MethodBinderParent();
		spork.getBinder().bind(methodBinderParent);

		assertEquals(bindMethodBinder.getMethodCount(), 2);
	}

	@Test
	public void invoke() throws NoSuchMethodException {
		MethodBinderParent object = new MethodBinderParent();
		spork.getBinder().bind(object);

		Method method = MethodBinderParent.class.getDeclaredMethod("privateCallCountMethod");
		assertEquals(0, object.getPrivateCallCount());

		Object regularResult = Reflection.invokeMethod(BindMethod.class, method, object);
		assertNull(regularResult);

		assertEquals(1, object.getPrivateCallCount());

		assertEquals(123, MethodBinderParent.staticEchoMethod(123));

		Method staticMethod = MethodBinderParent.class.getMethod("staticEchoMethod", int.class);
		Object staticResult = Reflection.invokeMethod(BindMethod.class, staticMethod, null, 123);
		assertNotNull(staticResult);
		assertEquals(123, staticResult);
	}
}
