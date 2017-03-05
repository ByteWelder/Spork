package spork.inject.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import spork.inject.Provides;

public class ObjectGraphBuilderTests {
	private static final class ModuleWithoutProvidesMethod {
	}

	private static final class Module {
		@Provides
		Object object() {
			return new Object();
		}
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testNoModules() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("no modules specified in ObjectGraphBuilder");
		new ObjectGraphBuilder().build();
	}

	@Test
	public void testModuleWithoutProvidesMethod() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Module spork.inject.internal.ObjectGraphBuilderTests$ModuleWithoutProvidesMethod has no public methods annotated with @Provides");
		new ObjectGraphBuilder()
				.module(new ModuleWithoutProvidesMethod())
				.build();
	}
}
