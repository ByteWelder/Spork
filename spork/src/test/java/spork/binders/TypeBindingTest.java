package spork.binders;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import spork.Spork;
import spork.exceptions.BindException;
import spork.interfaces.TypeBinder;

import static org.junit.Assert.assertEquals;

public class TypeBindingTest {
	private final Spork spork = new Spork();

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	private @interface BindValue {
		int value();
	}

	private interface IntSettable {
		void setValue(int value);
	}

	private static class BindTypeBinder implements TypeBinder<BindValue> {

		@Override
		public void bind(Object instance, BindValue annotation, Class<?> annotatedType, Object[] modules) {
			if (!IntSettable.class.isAssignableFrom(instance.getClass())) {
				throw new BindException(BindValue.class, instance.getClass(), "can only be used with IntSettable target");
			}

			IntSettable valueHolder = (IntSettable) instance;
			int value = annotation.value();
			valueHolder.setValue(value);
		}

		@Override
		public Class<BindValue> getAnnotationClass() {
			return BindValue.class;
		}
	}

	@BindValue(123)
	private static final class IntegerHolder implements IntSettable {
		private int value = 100;

		@Override
		public void setValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	@BindValue(123)
	private static final class FaultyIntegerHolder {
	}

	@Before
	public void registerTestBinders() {
		spork.getBinderRegistry().register(new BindTypeBinder());
	}

	@Test
	public void test() {
		IntegerHolder holder = new IntegerHolder();
		spork.getBinder().bind(holder);
		assertEquals(123, holder.getValue());
	}

	@Test(expected = BindException.class)
	public void testFaultyType() {
		FaultyIntegerHolder holder = new FaultyIntegerHolder();
		spork.getBinder().bind(holder);
	}
}
