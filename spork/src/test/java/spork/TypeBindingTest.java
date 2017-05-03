package spork;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import spork.exceptions.BindContext;
import spork.exceptions.BindContextBuilder;
import spork.exceptions.BindFailed;
import spork.exceptions.SporkRuntimeException;

import static org.junit.Assert.assertEquals;

public class TypeBindingTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private final SporkInstance spork = new SporkInstance();

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	private @interface BindValue {
		int value();
	}

	private interface IntSettable {
		void setValue(int value);
	}

	private static class BindTypeBinder implements spork.extension.TypeBinder<BindValue> {

		@Override
		public void bind(Object instance, BindValue annotation, Class<?> annotatedType, Object... parameters) throws BindFailed {
			if (!IntSettable.class.isAssignableFrom(instance.getClass())) {
				BindContext bindContext = new BindContextBuilder(BindValue.class).build();
				throw new BindFailed("Can only be used with IntSettable target", bindContext);
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
		spork.register(new BindTypeBinder());
	}

	@Test
	public void test() {
		IntegerHolder holder = new IntegerHolder();
		spork.bind(holder);
		assertEquals(123, holder.getValue());
	}

	@Test
	public void testFaultyType() {
		expectedException.expect(SporkRuntimeException.class);
		expectedException.expectMessage("Can only be used with IntSettable target");

		FaultyIntegerHolder holder = new FaultyIntegerHolder();
		spork.bind(holder);
	}
}
