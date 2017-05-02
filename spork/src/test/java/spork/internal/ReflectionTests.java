package spork.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
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

		Reflection.setFieldValue(field, fieldHolder, 1);

		assertThat(fieldHolder.field, is(1));
	}

	@Test
	public void setFieldValueOnFinalStaticField() throws NoSuchFieldException {
		expectedException.expect(UnexpectedException.class);
		expectedException.expectMessage("Failed to access spork.internal.ReflectionTests$FinalStaticFieldHolder.field");
		FinalStaticFieldHolder fieldHolder = new FinalStaticFieldHolder();
		Field field = FinalStaticFieldHolder.class.getDeclaredField("field");

		Reflection.setFieldValue(field, fieldHolder, 1);
		assertThat(fieldHolder.field, is(1));
	}

	@Test
	public void invokeMethod() throws NoSuchMethodException, InvocationTargetException {
		MethodHolder methodHolder = spy(new MethodHolder());
		Method method = MethodHolder.class.getDeclaredMethod("method");

		Reflection.invokeMethod(method, methodHolder);

		verify(methodHolder).method();
	}

	@Test
	public void invokeMethodThrowingException() throws NoSuchMethodException, InvocationTargetException {
		expectedException.expect(InvocationTargetException.class);

		ExceptionMethodHolder methodHolder = spy(new ExceptionMethodHolder());
		Method method = ExceptionMethodHolder.class.getDeclaredMethod("method");

		Reflection.invokeMethod(method, methodHolder);
	}
}
