package spork.inject.internal.lang;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NullabilityTests {

	private static class Testable {
		@Nullable
		Object nullableField;

		@Nonnull
		Object nonnullField = new Object();

		Object nonspecifiedField;

		@Nullable
		Object nullableMethod() {
			return null;
		}

		@Nonnull
		Object nonnullMethod() {
			return new Object();
		}

		Object nonspecifiedMethod() {
			return new Object();
		}

		private void nullableMethodArgument(@Nullable String argument) {
		}

		private void nonnullMethodArgument(@Nonnull String argument) {
		}

		private void nonspecifiedMethodArgument(String argument) {
		}
	}

	@Test
	public void fieldNullableTest() throws NoSuchFieldException, NoSuchMethodException {
		Field field = Testable.class.getDeclaredField("nullableField");
		Nullability nullability = Nullability.create(field);
		assertThat(nullability, is(Nullability.NULLABLE));
	}

	@Test
	public void fieldNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Field field = Testable.class.getDeclaredField("nonnullField");
		Nullability nullability = Nullability.create(field);
		assertThat(nullability, is(Nullability.NONNULL));
	}

	@Test
	public void fieldNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Field field = Testable.class.getDeclaredField("nonspecifiedField");
		Nullability nullability = Nullability.create(field);
		assertThat(nullability, is(Nullability.NONNULL));
	}

	@Test
	public void methodNullableTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nullableMethod = Testable.class.getDeclaredMethod("nullableMethod");
		Nullability nullability = Nullability.create(nullableMethod);
		assertThat(nullability, is(Nullability.NULLABLE));
	}

	@Test
	public void methodNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod("nonnullMethod");
		Nullability nullability = Nullability.create(method);
		assertThat(nullability, is(Nullability.NONNULL));
	}

	@Test
	public void methodNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod("nonspecifiedMethod");
		Nullability nullability = Nullability.create(method);
		assertThat(nullability, is(Nullability.NONNULL));
	}

	@Test
	public void methodArgumentNullableTest() throws NoSuchMethodException {
		Annotation[] method = getFirstMethodArgumentAnnotations("nullableMethodArgument");
		Nullability nullability = Nullability.create(method);
		assertThat(nullability, is(Nullability.NULLABLE));
	}

	@Test
	public void methodArgumentNonnullTest() throws NoSuchMethodException {
		Annotation[] methodArgumentAnnotations = getFirstMethodArgumentAnnotations("nonnullMethodArgument");
		Nullability nullability = Nullability.create(methodArgumentAnnotations);
		assertEquals(Nullability.NONNULL, nullability);
	}

	@Test
	public void methodArgumentNonspecifiedTest() throws NoSuchMethodException {
		Annotation[] methodArgumentAnnotations = getFirstMethodArgumentAnnotations("nonspecifiedMethodArgument");
		Nullability nullability = Nullability.create(methodArgumentAnnotations);
		assertEquals(Nullability.NONNULL, nullability);
	}

	private static Annotation[] getFirstMethodArgumentAnnotations(String methodName) throws NoSuchMethodException {
		Method method = Testable.class.getDeclaredMethod(methodName, String.class);
		assertThat(method.getParameterAnnotations().length, is(1));
		return method.getParameterAnnotations()[0];
	}
}
