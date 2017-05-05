package spork.inject.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import spork.inject.internal.lang.Nullability;
import spork.inject.internal.reflection.InjectSignature;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class InstanceCacheTests {
	private HashMap<InjectSignature, Object> map;
	private ReentrantLock mapLock;
	private InstanceCache instanceCache;
	private InstanceCache.Factory factory;

	@Before
	public void setup() {
		map = spy(new HashMap<InjectSignature, Object>());
		mapLock = spy(new ReentrantLock());
		instanceCache = new InstanceCache(map, mapLock);
		factory = mock(InstanceCache.Factory.class);
	}

	@Test
	public void construction() {
		assertThat(map.isEmpty(), is(true));
		assertThat(mapLock.isLocked(), is(false));
	}

	@Test
	public void caching() {
		InjectSignature injectSignature = anyInjectSignature();
		instanceCache.getOrCreate(injectSignature, factory);
		instanceCache.getOrCreate(injectSignature, factory);

		assertThat(map.size(), is(1));
	}

	@Test
	public void locking() {
		InjectSignature injectSignature = anyInjectSignature();

		instanceCache.getOrCreate(injectSignature, factory);

		InOrder inOrder = inOrder(map, mapLock);
		inOrder.verify(mapLock).lock();
		inOrder.verify(map).put(injectSignature, null);
		inOrder.verify(mapLock).unlock();
	}

	@Test
	public void lockingWithException() {
		InjectSignature injectSignature = anyInjectSignature();
		when(factory.create()).thenThrow(new RuntimeException());

		try {
			instanceCache.getOrCreate(injectSignature, factory);
		} catch (RuntimeException caught) {
			// catch, but do nothing
		}

		InOrder inOrder = inOrder(mapLock);
		inOrder.verify(mapLock).lock();
		inOrder.verify(mapLock).unlock();
		inOrder.verifyNoMoreInteractions();

		assertThat(map.isEmpty(), is(true));
	}

	private InjectSignature anyInjectSignature() {
		return new InjectSignature(getClass(), Nullability.NONNULL, null);
	}
}
