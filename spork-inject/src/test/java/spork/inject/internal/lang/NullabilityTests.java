package spork.inject.internal.lang;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;

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
		Field nullableField = Testable.class.getDeclaredField("nullableField");
		assertEquals(Nullability.NULLABLE, Nullability.create(nullableField));
	}

	@Test
	public void fieldNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Field nonnullField = Testable.class.getDeclaredField("nonnullField");
		assertEquals(Nullability.NONNULL, Nullability.create(nonnullField));
	}

	@Test
	public void fieldNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Field nonspecifiedField = Testable.class.getDeclaredField("nonspecifiedField");
		assertEquals(Nullability.NONNULL, Nullability.create(nonspecifiedField));
	}

	@Test
	public void methodNullableTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nullableMethod = Testable.class.getDeclaredMethod("nullableMethod");
		assertEquals(Nullability.NULLABLE, Nullability.create(nullableMethod));
	}

	@Test
	public void methodNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nonnullMethod = Testable.class.getDeclaredMethod("nonnullMethod");
		assertEquals(Nullability.NONNULL, Nullability.create(nonnullMethod));
	}

	@Test
	public void methodNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nonspecifiedMethod = Testable.class.getDeclaredMethod("nonspecifiedMethod");
		assertEquals(Nullability.NONNULL, Nullability.create(nonspecifiedMethod));
	}

	@Test
	public void methodArgumentNullableTest() throws NoSuchMethodException {
		Annotation[] methodArgumentAnnotations = getFirstMethodArgumentAnnotations("nullableMethodArgument");
		Nullability nullability = Nullability.create(methodArgumentAnnotations);
		assertEquals(Nullability.NULLABLE, nullability);
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
		Method methodArgumentsMethod = Testable.class.getDeclaredMethod(methodName, String.class);
		assertEquals(1, methodArgumentsMethod.getParameterAnnotations().length);
		 return methodArgumentsMethod.getParameterAnnotations()[0];
	}
}
