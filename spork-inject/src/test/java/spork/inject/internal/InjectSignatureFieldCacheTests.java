package spork.inject.internal;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import spork.inject.internal.lang.Nullability;
import spork.inject.internal.reflection.InjectSignatureFieldCache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class InjectSignatureFieldCacheTests {
	private spork.inject.internal.reflection.InjectSignatureFieldCache fieldCache;
	private spork.inject.internal.reflection.QualifierCache qualifierCache;
	private HashMap<Field, spork.inject.internal.reflection.InjectSignature> internalMap;

	private static class FieldTestable {
		public Object field;
	}

	@Before
	public void setup() {
		qualifierCache = spy(new spork.inject.internal.reflection.QualifierCache());
		internalMap = new HashMap<>();
		fieldCache = spy(new InjectSignatureFieldCache(qualifierCache, internalMap));
	}

	@Test
	public void testDefaultState() {
		assertThat(internalMap.isEmpty(), is(true));
	}

	@Test
	public void testFieldSignature() throws NoSuchFieldException {
		Field field = FieldTestable.class.getField("field");
		spork.inject.internal.reflection.InjectSignature signature = fieldCache.getInjectSignature(field, Object.class);
		assertEquals(new spork.inject.internal.reflection.InjectSignature(Object.class, Nullability.NONNULL, null), signature);
	}

	@Test
	public void testFieldCaching() throws NoSuchFieldException {
		Field field = FieldTestable.class.getField("field");
		fieldCache.getInjectSignature(field, Object.class);
		fieldCache.getInjectSignature(field, Object.class);

		assertThat(internalMap.size(), is(1));
	}
}
