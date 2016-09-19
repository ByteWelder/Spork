package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.inject.domain.SimpleComponent;
import io.github.sporklibrary.test.inject.domain.SimpleComponentModule;
import io.github.sporklibrary.test.inject.domain.SimpleInterfaceComponentModule;

import static org.junit.Assert.assertNotNull;

public class ComponentModuleTests {

	// todo: add test with inheritance

	public static class Parent {
		@Inject
		public SimpleComponent simpleComponent;

		@Inject
		public Runnable simpleInterfaceComponent;
	}

	@Test
	public void goodInjection() {
		Parent parent = new Parent();
		Spork.bind(parent, new SimpleComponentModule(), new SimpleInterfaceComponentModule());
		assertNotNull(parent.simpleComponent);
		assertNotNull(parent.simpleInterfaceComponent);
	}

	@Test(expected = BindException.class)
	public void oneMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent, new SimpleComponentModule());
	}

	@Test(expected = BindException.class)
	public void allMissingModuleInjection() {
		Parent parent = new Parent();
		Spork.bind(parent);
	}
}
