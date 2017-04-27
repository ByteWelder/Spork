package spork.inject.internal;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import spork.inject.internal.lang.Nullability;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class InjectSignatureCacheTests {
	private InjectSignatureCache cache;
	private Map<Field, InjectSignature> fieldInjectSignatureMap;
	private Map<Method, InjectSignature[]> methodInjectSignatureMap;

	public static class FieldTestable {
		public Object field;
	}

	public static class MethodTestable {
		public void method() {
		}
	}

	@Before
	public void setup() {
		fieldInjectSignatureMap = new HashMap<>();
		methodInjectSignatureMap = new HashMap<>();
		cache = new InjectSignatureCache(fieldInjectSignatureMap, methodInjectSignatureMap);
	}

	@Test
	public void testDefaultState() {
		assertThat(fieldInjectSignatureMap.isEmpty(), is(true));
		assertThat(methodInjectSignatureMap.isEmpty(), is(true));
	}

	@Test
	public void testFieldSignature() throws NoSuchFieldException {
		Field field = FieldTestable.class.getField("field");
		InjectSignature signature = cache.getInjectSignature(field, Object.class);
		assertEquals(new InjectSignature(Object.class, Nullability.NONNULL, null), signature);
	}

	@Test
	public void testFieldCaching() throws NoSuchFieldException {
		Field field = FieldTestable.class.getField("field");
		cache.getInjectSignature(field, Object.class);
		cache.getInjectSignature(field, Object.class);

		assertThat(fieldInjectSignatureMap.size(), is(1));
	}

	@Test
	public void testMethodSignature() throws NoSuchMethodException {
		Method method = MethodTestable.class.getMethod("method");
		InjectSignature[] signatures = cache.getInjectSignatures(method);

		assertThat(signatures, is(nullValue()));
		assertThat(methodInjectSignatureMap.size(), is(1));
	}

	@Test
	public void testMethodCaching() throws NoSuchMethodException {
		Method method = MethodTestable.class.getMethod("method");
		cache.getInjectSignatures(method);
		cache.getInjectSignatures(method);

		assertThat(methodInjectSignatureMap.size(), is(1));
	}
}
