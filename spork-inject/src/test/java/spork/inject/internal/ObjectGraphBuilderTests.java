package spork.inject.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import spork.inject.Provides;

public class ObjectGraphBuilderTests {
	private static final class ModuleWithoutProvidesMethod {
	}

	private static final class ModuleWithoutPublicProvidesMethod {
		@Provides
		Object object() {
			return new Object();
		}
	}

	private static final class Module {
		@Provides
		public Object object() {
			return new Object();
		}
	}

	@Scope
	@Retention(RetentionPolicy.RUNTIME)
	private @interface TestScope {
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testNoModules() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("No modules specified in ObjectGraphBuilder");
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

	@Test
	public void testModuleWithoutPublicProvidesMethod() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Module spork.inject.internal.ObjectGraphBuilderTests$ModuleWithoutPublicProvidesMethod has no public methods annotated with @Provides");
		new ObjectGraphBuilder()
				.module(new ModuleWithoutPublicProvidesMethod())
				.build();
	}

	@Test
	public void testScopedRootGraph() {
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage("Scope annotation can only be used when a parent ObjectGraph is specified");
		new ObjectGraphBuilder()
				.scope(TestScope.class)
				.module(new Module())
				.build();
	}
}
