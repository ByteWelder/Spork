package io.github.sporklibrary.test.fieldbinding;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.interfaces.FieldBinder;
import io.github.sporklibrary.interfaces.MethodBinder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests for combining Field and Method binding in 1 binder instance.
 */
public class FieldAndMethodBindingTests {

	private static class Parent {

		@BindFieldOrMethod
		private Object field;

		@BindFieldOrMethod
		public void test() {
		}

		@BindFieldOrMethod
		public static void testStatic() {
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.FIELD})
	@interface BindFieldOrMethod {
	}

	interface BindFieldAndMethodBinder extends FieldBinder<BindFieldOrMethod>, MethodBinder<BindFieldOrMethod> {
	}

	private static BindFieldAndMethodBinder createBinder(Spork spork) {
		BindFieldAndMethodBinder binder = Mockito.mock(BindFieldAndMethodBinder.class);
		when(binder.getAnnotationClass()).thenReturn(BindFieldOrMethod.class);

		spork.getBinderRegistry().register((FieldBinder<BindFieldOrMethod>) binder);
		spork.getBinderRegistry().register((MethodBinder<BindFieldOrMethod>) binder);

		return binder;
	}

	@Test
	public void methodBinding() throws NoSuchFieldException, NoSuchMethodException {
		Spork spork = new Spork();

		BindFieldAndMethodBinder binder = createBinder(spork);

		verifyZeroInteractions(binder);

		Parent parent = new Parent();
		spork.getBinder().bind(parent);

		Field field = Parent.class.getDeclaredField("field");
		BindFieldOrMethod fieldAnnotation = field.getAnnotation(BindFieldOrMethod.class);
		Method instanceMethod = Parent.class.getDeclaredMethod("test");
		BindFieldOrMethod instanceMethodAnnotation = instanceMethod.getAnnotation(BindFieldOrMethod.class);
		Method staticMethod = Parent.class.getDeclaredMethod("testStatic");
		BindFieldOrMethod staticMethodAnnotation = staticMethod.getAnnotation(BindFieldOrMethod.class);

		verify(binder, times(1)).bind(parent, fieldAnnotation, field, new Object[] {});
		verify(binder, times(1)).bind(parent, instanceMethodAnnotation, instanceMethod, new Object[] {});
		verify(binder, times(1)).bind(parent, staticMethodAnnotation, staticMethod, new Object[] {});
	}
}
