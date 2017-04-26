package spork.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.BindFailed;
import spork.stubs.TestAnnotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ReflectionTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	static class FieldHolder {
		int field;
	}

	static class FinalStaticFieldHolder {
		public static final int field = 1;
	}

	static class MethodHolder {
		public void method() {
		}
	}

	static class ExceptionMethodHolder {
		public void method() {
			throw new RuntimeException();
		}
	}

	@Test
	public void setFieldValue() throws NoSuchFieldException {
		FieldHolder fieldHolder = new FieldHolder();
		Field field = FieldHolder.class.getDeclaredField("field");

		Reflection.setFieldValue(TestAnnotation.class, field, fieldHolder, 1);

		assertThat(fieldHolder.field, is(1));
	}

	@Test
	public void setFieldValueOnFinalStaticField() throws NoSuchFieldException {
		expectedException.expect(BindFailed.class);
		expectedException.expectCause(isA(IllegalAccessException.class));
		expectedException.expectMessage("field is not accessible");
		FinalStaticFieldHolder fieldHolder = new FinalStaticFieldHolder();
		Field field = FinalStaticFieldHolder.class.getDeclaredField("field");

		Reflection.setFieldValue(TestAnnotation.class, field, fieldHolder, 1);
		assertThat(fieldHolder.field, is(1));
	}

	@Test
	public void invokeMethod() throws NoSuchMethodException {
		MethodHolder methodHolder = spy(new MethodHolder());
		Method method = MethodHolder.class.getDeclaredMethod("method");

		Reflection.invokeMethod(TestAnnotation.class, method, methodHolder);

		verify(methodHolder).method();
	}

	@Test
	public void invokeMethodThrowingException() throws NoSuchMethodException {
		expectedException.expect(BindFailed.class);
		expectedException.expectCause(isA(RuntimeException.class));
		expectedException.expectMessage("failed to invoke method");

		ExceptionMethodHolder methodHolder = spy(new ExceptionMethodHolder());
		Method method = MethodHolder.class.getDeclaredMethod("method");

		Reflection.invokeMethod(TestAnnotation.class, method, methodHolder);
	}
}
