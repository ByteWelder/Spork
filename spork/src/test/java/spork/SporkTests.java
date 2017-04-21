package spork;

import org.junit.Test;

import spork.extension.FieldBinder;
import spork.internal.Binder;
import spork.internal.Catalog;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SporkTests {

	@Test
	public void register() {
		Catalog catalog = mock(Catalog.class);
		SporkInstance spork = spy(new SporkInstance(null, catalog));

		FieldBinder fieldBinder = mock(FieldBinder.class);
		spork.register(fieldBinder);

		verify(catalog).add(fieldBinder);
	}

	@Test
	public void bind() {
		Binder binder = mock(Binder.class);
		SporkInstance spork = spy(new SporkInstance(binder, null));
		Object target = new Object();

		spork.bind(target);

		verify(binder).bind(target);
	}

	@Test(expected = IllegalStateException.class)
	public void registerAfterBind() {
		SporkInstance spork = new SporkInstance();

		spork.bind(this);
		spork.register(mock(FieldBinder.class));
	}
}
