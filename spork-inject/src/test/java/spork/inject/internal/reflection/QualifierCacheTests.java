package spork.inject.internal.reflection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Scope;
import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyZeroInteractions;

public class QualifierCacheTests {
	private Map<Class<? extends Annotation>, Method> bindActionMap;
	private QualifierCache cache;
	private ReentrantLock lock;

	@Before
	public void setup() {
		bindActionMap = new HashMap<>();
		lock = spy(new ReentrantLock());
		cache = spy(new QualifierCache(bindActionMap, lock));
	}

	@Test
	public void initialState() {
		assertThat(bindActionMap.size(), is(0));
		assertThat(lock.isLocked(), is(false));
	}

	@Test
	public void methodCaching() {
		Annotation annotation = Singleton.class.getAnnotation(Scope.class);
		// call twice - should cache once
		cache.getQualifier(annotation);
		cache.getQualifier(annotation);

		assertThat(bindActionMap.size(), is(1));
	}

	@Test
	public void annotationWithoutValueMethod() {
		Annotation annotation = Singleton.class.getAnnotation(Scope.class);
		cache.getQualifier(annotation);

		InOrder inOrder = inOrder(lock, cache);

		// initial call
		inOrder.verify(cache).getQualifier(annotation);

		// internal thread safe method lookup
		inOrder.verify(cache).getValueMethodThreadSafe(Scope.class);
		inOrder.verify(lock).lock();
		inOrder.verify(cache).getValueMethod(Scope.class);
		inOrder.verify(lock).unlock();
		// no value Method found, so no invocation needed

		inOrder.verifyNoMoreInteractions();
		verifyZeroInteractions(cache);
		assertThat(bindActionMap.size(), is(1));
	}

	@Test
	public void annotationWithValueMethod() {
		Annotation annotation = Singleton.class.getAnnotation(Retention.class);
		cache.getQualifier(annotation);

		InOrder inOrder = inOrder(lock, cache);

		// initial call
		inOrder.verify(cache).getQualifier(annotation);

		// internal thread safe method lookup
		inOrder.verify(cache).getValueMethodThreadSafe(Retention.class);
		inOrder.verify(lock).lock();
		inOrder.verify(cache).getValueMethod(Retention.class);
		inOrder.verify(lock).unlock();

		// get Qualifier from value() method
		inOrder.verify(cache).getQualifier(any(Method.class), eq(annotation));

		inOrder.verifyNoMoreInteractions();
		verifyZeroInteractions(cache);
		assertThat(bindActionMap.size(), is(1));
	}
}
