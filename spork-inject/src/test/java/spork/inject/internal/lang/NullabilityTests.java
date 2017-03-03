package spork.inject.internal.lang;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;

public class NullabilityTests {

	public static class Testable {
		@Nullable
		public Object nullableField;

		@Nonnull
		public Object nonnullField = new Object();

		public Object nonspecifiedField;

		@Nullable
		public Object nullableMethod() {
			return null;
		}

		@Nonnull
		public Object nonnullMethod() {
			return new Object();
		}

		public Object nonspecifiedMethod() {
			return new Object();
		}

		public void nullableMethodArgument(@Nullable String argument) {
		}

		public void nonnullMethodArgument(@Nonnull String argument) {
		}

		public void nonspecifiedMethodArgument(String argument) {
		}
	}

	@Test
	public void fieldNullableTest() throws NoSuchFieldException, NoSuchMethodException {
		Field nullableField = Testable.class.getField("nullableField");
		assertEquals(Nullability.NULLABLE, Nullability.create(nullableField));
	}

	@Test
	public void fieldNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Field nonnullField = Testable.class.getField("nonnullField");
		assertEquals(Nullability.NONNULL, Nullability.create(nonnullField));
	}

	@Test
	public void fieldNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Field nonspecifiedField = Testable.class.getField("nonspecifiedField");
		assertEquals(Nullability.NONNULL, Nullability.create(nonspecifiedField));
	}

	@Test
	public void methodNullableTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nullableMethod = Testable.class.getMethod("nullableMethod");
		assertEquals(Nullability.NULLABLE, Nullability.create(nullableMethod));
	}

	@Test
	public void methodNonnullTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nonnullMethod = Testable.class.getMethod("nonnullMethod");
		assertEquals(Nullability.NONNULL, Nullability.create(nonnullMethod));
	}

	@Test
	public void methodNonspecifiedTest() throws NoSuchFieldException, NoSuchMethodException {
		Method nonspecifiedMethod = Testable.class.getMethod("nonspecifiedMethod");
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
		Method methodArgumentsMethod = Testable.class.getMethod(methodName, String.class);
		assertEquals(1, methodArgumentsMethod.getParameterAnnotations().length);
		 return methodArgumentsMethod.getParameterAnnotations()[0];
	}
}
