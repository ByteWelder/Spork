package io.github.sporklibrary.test.inject;

import org.junit.Test;

import io.github.sporklibrary.Lazy;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.annotations.Inject;
import io.github.sporklibrary.annotations.NonNull;
import io.github.sporklibrary.annotations.Nullable;
import io.github.sporklibrary.exceptions.BindException;
import io.github.sporklibrary.test.inject.domain.SimpleComponent;
import io.github.sporklibrary.test.inject.domain.SimpleComponentModule;
import io.github.sporklibrary.test.inject.domain.SimpleComponentNullModule;
import io.github.sporklibrary.test.inject.domain.SimpleInterfaceComponentModule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class InjectTests {

	// todo: add test with inheritance

	private static class Parent {
		@Inject	SimpleComponent simpleComponent;
		@Inject Runnable simpleInterfaceComponent;
	}

	private static class SimpleParent {
		@Inject	SimpleComponent simpleComponent;
	}

	private static class SimpleParentNullable {
		@Inject	@Nullable SimpleComponent simpleComponent;
	}

	private static class SimpleParentNonNull {
		@Inject	@NonNull SimpleComponent simpleComponent;
	}

	private static class SimpleParentLazy {
		@Inject Lazy<SimpleComponent> simpleComponent;
	}

	@Test
	public void regularInjection() {
		// normal case with a class-bound and interface-bound field
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

	@Test(expected = BindException.class)
	public void nullValueWithoutNullable() {
		SimpleParent parent = new SimpleParent();
		Spork.bind(parent, new SimpleComponentNullModule());
	}

	@Test
	public void nullValueWithNullable() {
		SimpleParentNullable parent = new SimpleParentNullable();
		Spork.bind(parent, new SimpleComponentNullModule());
		assertNull(parent.simpleComponent);
	}

	@Test(expected = BindException.class)
	public void nullValueWithNonNull() {
		SimpleParentNonNull parent = new SimpleParentNonNull();
		Spork.bind(parent, new SimpleComponentNullModule());
	}

	@Test
	public void instanceValueWithNonNull() {
		SimpleParentNonNull parent = new SimpleParentNonNull();
		Spork.bind(parent, new SimpleComponentModule());
		assertNotNull(parent.simpleComponent);
	}

	@Test
	public void lazyValue() {
		SimpleParentLazy parent = new SimpleParentLazy();
		Spork.bind(parent, new SimpleComponentModule());

		assertNotNull(parent.simpleComponent);

		SimpleComponent first = parent.simpleComponent.get();
		assertNotNull(first);

		SimpleComponent second = parent.simpleComponent.get();
		assertNotNull(second);
		assertEquals(first, second);
	}
}
