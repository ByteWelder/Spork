package spork.inject.internal;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;

import spork.inject.internal.reflection.InjectSignatureMethodCache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;

public class InjectSignatureMethodCacheTests {
	private spork.inject.internal.reflection.InjectSignatureMethodCache methodCache;
	private spork.inject.internal.reflection.QualifierCache qualifierCache;
	private HashMap<Method, spork.inject.internal.reflection.InjectSignature[]> internalMap;

	private static class MethodTestable {
		public void method() {
		}
	}

	@Before
	public void setup() {
		qualifierCache = spy(new spork.inject.internal.reflection.QualifierCache());
		internalMap = new HashMap<>();
		methodCache = spy(new InjectSignatureMethodCache(qualifierCache, internalMap));
	}

	@Test
	public void testDefaultState() {
		assertThat(internalMap.isEmpty(), is(true));
	}

	@Test
	public void testMethodSignature() throws NoSuchMethodException {
		Method method = MethodTestable.class.getMethod("method");
		spork.inject.internal.reflection.InjectSignature[] signatures = methodCache.getInjectSignatures(method);

		assertThat(signatures, is(nullValue()));
		assertThat(internalMap.size(), is(1));
	}

	@Test
	public void testMethodCaching() throws NoSuchMethodException {
		Method method = MethodTestable.class.getMethod("method");
		methodCache.getInjectSignatures(method);
		methodCache.getInjectSignatures(method);

		assertThat(internalMap.size(), is(1));
	}
}
