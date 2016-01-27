package io.github.sporklibrary;

import io.github.sporklibrary.annotations.BindMethod;
import io.github.sporklibrary.binders.AnnotatedMethod;
import io.github.sporklibrary.binders.AnnotatedMethods;
import io.github.sporklibrary.binders.TestMethodBinder;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class MethodBindingTests
{
	private TestMethodBinder mTestMethodBinder;

	public static class MethodBinderParent
	{
		private int mTestCallCount = 0;

		public MethodBinderParent()
		{
			Spork.bind(this);
		}

		@BindMethod
		private void privateCallCountMethod()
		{
			mTestCallCount++;
		}

		@BindMethod
		public static int staticEchoMethod(int a)
		{
			return a;
		}

		public int getPrivateCallCount()
		{
			return mTestCallCount;
		}
	}

	@Before
	public void registerTestBinders()
	{
		mTestMethodBinder = new TestMethodBinder();
		Spork.getBinderManager().register(mTestMethodBinder);
	}

	@Test
	public void methodBinding()
	{
		assertEquals(mTestMethodBinder.getMethodCount(), 0);

		new MethodBinderParent();

		assertEquals(mTestMethodBinder.getMethodCount(), 2);
	}

	@Test
	public void invoke() throws NoSuchMethodException
	{
		MethodBinderParent object = new MethodBinderParent();

		Method method = MethodBinderParent.class.getDeclaredMethod("privateCallCountMethod");
		BindMethod annotation = method.getAnnotation(BindMethod.class);
		AnnotatedMethod<BindMethod> annotated_method = new AnnotatedMethod<>(annotation, method);

		// Testing AnnotatedMethod
		assertEquals(annotation, annotated_method.getAnnotation());
		assertEquals(method, annotated_method.getMethod());

		assertEquals(0, object.getPrivateCallCount());

		Object regular_result = AnnotatedMethods.invoke(annotated_method, object);
		assertNull(regular_result);

		assertEquals(1, object.getPrivateCallCount());

		Method static_method = MethodBinderParent.class.getMethod("staticEchoMethod", int.class);
		BindMethod static_method_annotation = static_method.getAnnotation(BindMethod.class);
		AnnotatedMethod<BindMethod> static_annotated_method = new AnnotatedMethod<>(static_method_annotation, static_method);

		assertEquals(123, MethodBinderParent.staticEchoMethod(123));

		Object static_result = AnnotatedMethods.invoke(static_annotated_method, null, 123);
		assertNotNull(static_result);
		assertTrue(static_result.equals(123));
	}
}
