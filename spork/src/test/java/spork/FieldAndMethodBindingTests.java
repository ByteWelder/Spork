package spork;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import spork.extension.MethodBinder;

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
			// no-op
		}

		@BindFieldOrMethod
		public static void testStatic() {
			// no-op
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.FIELD})
	@interface BindFieldOrMethod {
	}

	interface BindFieldAndMethodBinder extends spork.extension.FieldBinder<BindFieldOrMethod>, spork.extension.MethodBinder<BindFieldOrMethod> {
	}

	private static BindFieldAndMethodBinder createBinder(SporkInstance spork) {
		BindFieldAndMethodBinder binder = Mockito.mock(BindFieldAndMethodBinder.class);
		when(binder.getAnnotationClass()).thenReturn(BindFieldOrMethod.class);

		spork.register((spork.extension.FieldBinder<BindFieldOrMethod>) binder);
		spork.register((MethodBinder<BindFieldOrMethod>) binder);

		return binder;
	}

	@Test
	public void methodBinding() throws NoSuchFieldException, NoSuchMethodException {
		SporkInstance spork = new SporkInstance();

		BindFieldAndMethodBinder binder = createBinder(spork);

		verifyZeroInteractions(binder);

		Parent parent = new Parent();
		spork.bind(parent);

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
