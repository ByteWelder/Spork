package spork;

import org.junit.Test;

import spork.extension.FieldBinder;
import spork.extension.MethodBinder;
import spork.extension.TypeBinder;
import spork.internal.Binder;
import spork.internal.Catalog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SporkInstanceTests {

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

	@Test(expected = IllegalStateException.class)
	public void registerFieldBinderAfterBind() {
		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(FieldBinder.class));
	}

	@Test(expected = IllegalStateException.class)
	public void registerMethodBinderAfterBind() {
		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(MethodBinder.class));
	}

	@Test(expected = IllegalStateException.class)
	public void registerTypeBinderAfterBind() {
		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(TypeBinder.class));
	}

	@Test
	public void bind() {
		Binder binder = mock(Binder.class);
		SporkInstance spork = spy(new SporkInstance(binder, null));
		Object target = new Object();

		spork.bind(target);

		verify(binder).bind(target);
	}
}
