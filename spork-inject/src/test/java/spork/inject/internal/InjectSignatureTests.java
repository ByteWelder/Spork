package spork.inject.internal;

import org.junit.Test;

import java.lang.annotation.Annotation;

import javax.inject.Named;

import spork.inject.internal.lang.Nullability;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class InjectSignatureTests {
	private final spork.inject.internal.reflection.QualifierCache qualifierCache = new spork.inject.internal.reflection.QualifierCache();
//	private final InjectSignatureFieldCache fieldCache = new InjectSignatureFieldCache(qualifierCache);
//	private final InjectSignatureMethodCache methodCache = new InjectSignatureMethodCache(qualifierCache);
//	private final ReflectionCache reflectionCache = new ReflectionCache(qualifierCache, fieldCache, methodCache);

	private static class AnnotationHolder {
		@Named("first")
		public String first;

		@Named("second")
		public String second;
	}

	@Test
	public void testGetMethods() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		String qualifier = qualifierCache.getQualifier(annotation);
		spork.inject.internal.reflection.InjectSignature signature = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, qualifier);

		assertThat(signature.getType(), is((Object) String.class));
		assertThat(signature.getNullability(), is(Nullability.NONNULL));
		assertThat(signature.hasQualifier(), is(true));
	}

	@Test
	public void testEqualsTypeMismatch() {
		spork.inject.internal.reflection.InjectSignature signature = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, null);
		assertFalse(signature.equals(new Object()));
	}

	@Test
	public void matchAllTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		String qualifier = qualifierCache.getQualifier(annotation);
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, qualifier);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, qualifier);

		assertEquals(first, second);
		assertEquals(second, first);
		assertEquals(first.hashCode(), second.hashCode());
		assertEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndAnnotationTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		String qualifier = qualifierCache.getQualifier(annotation);
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, qualifier);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NULLABLE, qualifier);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityTest() throws NoSuchFieldException {
		Annotation firstAnnotation = getFirstAnnotation();
		Annotation secondAnnotation = getSecondAnnotation();
		String firstQualifier = qualifierCache.getQualifier(firstAnnotation);
		String secondQualifier = qualifierCache.getQualifier(secondAnnotation);
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, firstQualifier);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, secondQualifier);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityWithSingleNullQualifierTest() throws NoSuchFieldException {
		Annotation firstAnnotation = getFirstAnnotation();
		String firstQualifier = qualifierCache.getQualifier(firstAnnotation);
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, firstQualifier);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, null);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	@Test
	public void matchClassAndNullabilityWithBothNullQualifiersTest() throws NoSuchFieldException {
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, null);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, null);

		assertEquals(first, second);
		assertEquals(second, first);
		assertEquals(first.hashCode(), second.hashCode());
		assertEquals(first.toString(), second.toString());
	}

	@Test
	public void matchNullabilityAndAnnotationTest() throws NoSuchFieldException {
		Annotation annotation = getFirstAnnotation();
		String qualifier = qualifierCache.getQualifier(annotation);
		spork.inject.internal.reflection.InjectSignature first = new spork.inject.internal.reflection.InjectSignature(String.class, Nullability.NONNULL, qualifier);
		spork.inject.internal.reflection.InjectSignature second = new spork.inject.internal.reflection.InjectSignature(Integer.class, Nullability.NONNULL, qualifier);

		assertNotEquals(first, second);
		assertNotEquals(second, first);
		assertNotEquals(first.hashCode(), second.hashCode());
		assertNotEquals(first.toString(), second.toString());
	}

	private static Annotation getFirstAnnotation() throws NoSuchFieldException {
		return AnnotationHolder.class.getField("first").getAnnotation(Named.class);
	}

	private static Annotation getSecondAnnotation() throws NoSuchFieldException {
		return AnnotationHolder.class.getField("second").getAnnotation(Named.class);
	}
}
