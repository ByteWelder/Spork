package spork.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BindActionCacheTests {
	private Map<Class<?>, List<BindAction>> bindActionMap;
	private BindActionCache cache;
	private Lock lock;

	@Before
	public void setup() {
		bindActionMap = new HashMap<>();
		lock = spy(new ReentrantLock());
		cache = new BindActionCache(bindActionMap, lock);
	}

	@Test
	public void lockOrder() {
		BindActionCache.Factory factory = mock(BindActionCache.Factory.class);
		cache.getOrCreate(String.class, factory);

		InOrder inOrder = inOrder(lock, factory);
		inOrder.verify(lock).lock();
		inOrder.verify(factory).create(String.class);
		inOrder.verify(lock).unlock();
	}

	@Test
	public void factoryCreationCalled() {
		List<BindAction> bindActions = new ArrayList<>();
		BindActionCache.Factory factory = mock(BindActionCache.Factory.class);
		when(factory.create(String.class)).thenReturn(bindActions);

		List<BindAction> resultActions = cache.getOrCreate(String.class, factory);

		assertThat(resultActions, is(bindActions));
		assertThat(bindActionMap.size(), is(1));
	}

	@Test
	public void factoryCreationHappensOnlyOnce() {
		BindActionCache.Factory factory = mock(BindActionCache.Factory.class);

		cache.getOrCreate(String.class, factory);
		cache.getOrCreate(String.class, factory);

		verify(factory).create(String.class);
		assertThat(bindActionMap.size(), is(1));
	}
}
