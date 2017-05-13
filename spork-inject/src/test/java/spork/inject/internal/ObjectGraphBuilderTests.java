package spork.inject.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import spork.exceptions.SporkRuntimeException;
import spork.inject.ObjectGraphs;
import spork.inject.Provides;

public class ObjectGraphBuilderTests {

	static final class NonPublicModule {
	}

	public static final class ModuleWithoutProvidesMethod {
	}

	public static final class ModuleWithoutPublicProvidesMethod {
		@Provides
		Object object() {
			return new Object();
		}
	}

	public static final class Module {
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
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("No modules specified in ObjectGraphBuilder");
		ObjectGraphs.builder().build();
	}

	@Test
	public void testNonPublicModule() {
		String message = "Module class isn't a public class: " + NonPublicModule.class.getName();
		expectedException.expectMessage(message);
		expectedException.expect(SporkRuntimeException.class);
		ObjectGraphs.builder()
				.module(new NonPublicModule())
				.build();
	}

	@Test
	public void testModuleWithoutProvidesMethod() {
		String message = "No methods found annotated with @Provides for " + ModuleWithoutProvidesMethod.class.getName();
		expectedException.expectMessage(message);
		expectedException.expect(SporkRuntimeException.class);
		ObjectGraphs.builder()
				.module(new ModuleWithoutProvidesMethod())
				.build();
	}

	@Test
	public void testModuleWithoutPublicProvidesMethod() {
		String message = "Module method is not public: java.lang.Object "
				+ ModuleWithoutPublicProvidesMethod.class.getName() + ".object()";
		expectedException.expectMessage(message);
		expectedException.expect(SporkRuntimeException.class);
		ObjectGraphs.builder()
				.module(new ModuleWithoutPublicProvidesMethod())
				.build();
	}

	@Test
	public void testScopedRootGraph() {
		String message = "Scope annotation can only be used when a parent ObjectGraph is specified";
		expectedException.expectMessage(message);
		expectedException.expect(SporkRuntimeException.class);
		ObjectGraphs.builder()
				.scope(TestScope.class)
				.module(new Module())
				.build();
	}
}
