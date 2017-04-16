package spork;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import spork.internal.Reflection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class FieldBindingTests {

	private static class Parent {
		@BindField
		private String instanceField;

		@BindField
		private static Object staticField;

		public static Object getStaticField() {
			return staticField;
		}

		public String getInstanceField() {
			return instanceField;
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	@interface BindField {
	}

	private class StringFieldBinder implements FieldBinder<BindField> {
		@Override
		public void bind(Object object, BindField annotation, Field field, Object... parameters) {
			if ("instanceField".equals(field.getName())) {
				Reflection.setFieldValue(BindField.class, field, object, "1");
			} else if ("staticField".equals(field.getName())) {
				Reflection.setFieldValue(BindField.class, field, object, "2");
			}
		}

		@Override
		public Class<BindField> getAnnotationClass() {
			return BindField.class;
		}
	}

	@Test
	public void testMethodCalls() throws NoSuchFieldException, NoSuchMethodException {
		Spork spork = new Spork();
		StringFieldBinder binder = Mockito.mock(StringFieldBinder.class);
		spork.getBinderRegistry().register(binder);

		when(binder.getAnnotationClass()).thenReturn(BindField.class);

		verifyZeroInteractions(binder);

		Parent parent = new Parent();
		spork.getBinder().bind(parent);

		Field instanceField = Parent.class.getDeclaredField("instanceField");
		BindField instanceAnnotation = instanceField.getAnnotation(BindField.class);
		verify(binder, times(1)).bind(parent, instanceAnnotation, instanceField, new Object[] {});

		Field staticField = Parent.class.getDeclaredField("staticField");
		BindField staticAnnotation = staticField.getAnnotation(BindField.class);
		verify(binder, times(1)).bind(parent, staticAnnotation, staticField, new Object[] {});
	}

	@Test
	public void testValues() {
		Spork spork = new Spork();
		StringFieldBinder binder = new StringFieldBinder();
		spork.getBinderRegistry().register(binder);

		Parent parent = new Parent();
		spork.getBinder().bind(parent);

		assertEquals("1", parent.getInstanceField());
		assertEquals("2", Parent.getStaticField());
	}
}
