package spork.inject.internal;

import org.junit.Test;

import java.lang.annotation.Annotation;

import javax.inject.Named;
import javax.inject.Singleton;

import spork.inject.internal.InjectSignature;
import spork.inject.internal.lang.Nullability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class InjectSignatureTests {

	public static class AnnotationHolder {
		@Named("first")
		public String first;

		@Singleton
		public String second;
	}

	@Test
	public void testGetMethods() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		InjectSignature signature = new InjectSignature(String.class, Nullability.NONNULL, annotation);

		assertEquals(String.class, signature.getType());
		assertEquals(Nullability.NONNULL, signature.getNullability());
		assertEquals(true, signature.hasQualifier());
	}

	@Test
	public void testEqualsTypeMismatch() {
		InjectSignature signature = new InjectSignature(String.class, Nullability.NONNULL, null);
		assertFalse(signature.equals(new Object()));
	}

	@Test
	public void matchAllTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, annotation);
		InjectSignature second = new InjectSignature(String.class, Nullability.NONNULL, annotation);

		assertEquals(first, second);
		assertEquals(second, first);
		assertEquals(first.hashCode(), second.hashCode());
		assertEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndAnnotationTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, annotation);
		InjectSignature second = new InjectSignature(String.class, Nullability.NULLABLE, annotation);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityTest() throws NoSuchFieldException {
		Annotation firstAnnotation = getFirstAnnotation();
		Annotation secondAnnotation = getSecondAnnotation();
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, firstAnnotation);
		InjectSignature second = new InjectSignature(String.class, Nullability.NONNULL, secondAnnotation);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityWithSingleNullQualifierTest() throws NoSuchFieldException {
		Annotation firstAnnotation = getFirstAnnotation();
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, firstAnnotation);
		InjectSignature second = new InjectSignature(String.class, Nullability.NONNULL, null);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityWithBothNullQualifiersTest() throws NoSuchFieldException {
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, null);
		InjectSignature second = new InjectSignature(String.class, Nullability.NONNULL, null);

		assertEquals(first, second);
		assertEquals(second, first);
		assertEquals(first.hashCode(), second.hashCode());
		assertEquals(first.toString(), second.toString());
	}

	@Test
	public void matchNullabilityAndAnnotationTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		InjectSignature first = new InjectSignature(String.class, Nullability.NONNULL, annotation);
		InjectSignature second = new InjectSignature(Integer.class, Nullability.NONNULL, annotation);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	private static Annotation getFirstAnnotation() throws NoSuchFieldException {
		return AnnotationHolder.class.getField("first").getAnnotation(Named.class);
	}

	private static Annotation getSecondAnnotation() throws NoSuchFieldException {
		return AnnotationHolder.class.getField("second").getAnnotation(Singleton.class);
	}
}
