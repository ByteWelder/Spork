package spork;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.exceptions.BindFailed;
import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.Binder;
import spork.internal.Catalog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SporkInstanceTests {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void registerFieldBinder() {
		Catalog catalog = mock(Catalog.class);
		SporkInstance spork = spy(new SporkInstance(null, catalog));

		FieldBinder fieldBinder = mock(FieldBinder.class);
		spork.register(fieldBinder);

		verify(catalog).add(fieldBinder);
	}

	@Test
	public void registerMethodBinder() {
		Catalog catalog = mock(Catalog.class);
		SporkInstance spork = spy(new SporkInstance(null, catalog));

		MethodBinder methodBinder = mock(MethodBinder.class);
		spork.register(methodBinder);

		verify(catalog).add(methodBinder);
	}

	@Test
	public void registerTypeBinder() {
		Catalog catalog = mock(Catalog.class);
		SporkInstance spork = spy(new SporkInstance(null, catalog));

		TypeBinder typeBinder = mock(TypeBinder.class);
		spork.register(typeBinder);

		verify(catalog).add(typeBinder);
	}

	@Test
	public void registerFieldBinderAfterBind() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("binders must be registered before the first bind() is called");

		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(FieldBinder.class));
	}

	@Test
	public void registerMethodBinderAfterBind() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("binders must be registered before the first bind() is called");

		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(MethodBinder.class));
	}

	@Test
	public void registerTypeBinderAfterBind() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("binders must be registered before the first bind() is called");

		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(TypeBinder.class));
	}

	@Test
	public void bind() throws BindFailed {
		Binder binder = mock(Binder.class);
		SporkInstance spork = spy(new SporkInstance(binder, null));
		Object target = new Object();

		spork.bind(target);

		verify(binder).bind(target);
	}
}
