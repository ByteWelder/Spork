package spork.test.typebinding;

import org.junit.Before;
import org.junit.Test;

import spork.Spork;
import spork.exceptions.BindException;

import static org.junit.Assert.assertEquals;

public class TypeBindingTest {
	private final Spork spork = new Spork();

	@BindValue(123)
	public final class IntegerHolder implements IntSettable {
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
	public final class FaultyIntegerHolder {
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
