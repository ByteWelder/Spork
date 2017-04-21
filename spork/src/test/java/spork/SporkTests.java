package spork;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.BindActionCache;
import spork.internal.Binder;
import spork.internal.Registry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SporkTests {
	@Mock Registry registry;
	@Mock BindActionCache bindActionCache;
	@Mock Binder binder;
	private Spork spork;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		spork = spy(new Spork(registry, bindActionCache, binder));
	}

	@Test
	public void bind() {
		spork.bind(this);
		verify(spork).bind(this);
	}

	@Test
	public void registerFieldBinder() {
		spork.extension.FieldBinder binder = mock(spork.extension.FieldBinder.class);
		spork.register(binder);
		verify(registry).register(binder);
		verify(bindActionCache).register(binder);
	}

	@Test
	public void registerMethodBinder() {
		spork.extension.MethodBinder binder = mock(MethodBinder.class);
		spork.register(binder);
		verify(registry).register(binder);
		verify(bindActionCache).register(binder);
	}

	@Test
	public void registerTypeBinder() {
		spork.extension.TypeBinder binder = mock(TypeBinder.class);
		spork.register(binder);
		verify(registry).register(binder);
		verify(bindActionCache).register(binder);
	}
}
